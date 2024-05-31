package com.teamsparta.mini5foodfeed.domain.feed.service

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.model.Tag
import com.teamsparta.mini5foodfeed.domain.feed.model.toResponse
import com.teamsparta.mini5foodfeed.domain.feed.model.updateTag
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository

import com.teamsparta.mini5foodfeed.common.exception.ModelNotFoundException
import com.teamsparta.mini5foodfeed.common.exception.NotAuthenticationException
import com.teamsparta.mini5foodfeed.domain.feed.dto.*
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepositoryImpl

import com.teamsparta.mini5foodfeed.domain.feed.repository.TagRepository
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository


import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val feedRepositoryImpl: FeedRepositoryImpl,
    private val commentRepository: CommentRepository,
    private val tagRepository: TagRepository,
    private val userRepository: UserRepository,
) {

    fun getFeedList(
        cursor: Int?,
        tagVo: TagVo
    ): CursorPageResponse {
        val pageable = PageRequest.of(0,20, Sort.Direction.DESC, "createdAt")
        val feedSlice : Slice<Feed>  = feedRepositoryImpl.findAllByTagWithCursor(tagVo, cursor, pageable)
        val nextCursor = if (feedSlice.hasNext()) feedSlice.nextPageable().pageNumber else null
        val pageRequest = PageRequest.of(0,5)

        val feedResponseWithComments = feedSlice.content.map{ feed ->
            val comments = commentRepository.findTop5ByFeedIdOrderByCreatedAtDesc(feed.id,pageRequest)
                .map { comment -> CommentResponse(comment.contents, comment.createdAt)}
            feed.toResponse().copy(comments = comments)
        }


        return CursorPageResponse(feedResponseWithComments, nextCursor)
    }

    fun getFeedDetail(feedId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        return feed.toResponse()
    }

    @Transactional
    fun createFeed(feedRequest: CreateFeedRequest, userId:Long): FeedResponse {
        val user: Users? = userRepository.findByIdOrNull(userId)

        val tag = Tag(
            feedRequest.tagVo.sweet,
            feedRequest.tagVo.hot,
            feedRequest.tagVo.spicy,
            feedRequest.tagVo.cool,
            feedRequest.tagVo.sweetMood,
            feedRequest.tagVo.dateCourse
        )
        tagRepository.save(tag)

        val feed = feedRepository.save(
            Feed(
                title = feedRequest.title,
                description = feedRequest.description,
                createdAt = LocalDateTime.now(),
                comments = null,
                user = user,
                tag =tag
            )
        )
        feed.tag.feed = feed

        return feed.toResponse()
    }

    @Transactional
    fun updateFeed(feedId : Long, request: UpdateFeedRequest, userId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        if (userId != feed.user!!.id)  throw NotAuthenticationException("feed")

        val (title, description) = request
            feed.title = title
            feed.description = description
        feed.updateTag(request.tagVo)

        return feed.toResponse()
    }

    @Transactional
    fun deleteFeed(feedId: Long, userId: Long) {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        if (userId != feed.user!!.id)  throw NotAuthenticationException("feed")
        tagRepository.delete(feed.tag)
        feedRepository.delete(feed)
    }
}