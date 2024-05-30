package com.teamsparta.mini5foodfeed.domain.feed.service

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.dto.CreateFeedRequest
import com.teamsparta.mini5foodfeed.domain.feed.dto.CursorPageResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.UpdateFeedRequest
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.model.Tag
import com.teamsparta.mini5foodfeed.domain.feed.model.toResponse
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.domain.feed.repository.TagRepository
import com.teamsparta.mini5foodfeed.exception.ModelNotFoundException
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
    private val commentRepository: CommentRepository,
    private val tagRepository: TagRepository
) {

    fun getFeedList(
        cursor: Int?
    ): CursorPageResponse {
        val pageable = PageRequest.of(0,20, Sort.Direction.DESC, "createdAt")
        val feedSlice : Slice<FeedResponse>  = feedRepository.findAllByCursor(cursor, pageable)
        val nextCursor = if (feedSlice.hasNext()) feedSlice.nextPageable().pageNumber else null
        val pageRequest = PageRequest.of(0,5)

        val feedResponseWithComments = feedSlice.map{ feedResponse ->
            val comments = commentRepository.findTop5ByFeedIdOrderByCreatedAtDesc(feedResponse.id,pageRequest)
                .map { comment -> CommentResponse(comment.contents, comment.createdAt)}
            feedResponse.copy(comments = comments)
        }


        return CursorPageResponse(feedResponseWithComments, nextCursor)
    }

    fun getFeedDetail(feedId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        return feed.toResponse()
    }

    @Transactional
    fun createFeed(feedRequest: CreateFeedRequest): FeedResponse {
        // val user: User = TODO : 인증,인가 과정에서 유저 찾아오고 밑에 save 에서 초기화된 이 유저를 저장

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
               // user = User,
                tag =tag
            )
        )
        feed.tag.feed = feed

        return feed.toResponse()
    }

    @Transactional
    fun updateFeed(feedId : Long, request: UpdateFeedRequest): FeedResponse {
        // TODO : 유저 인증/인가
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        val (title, description) = request
            feed.title = title
            feed.description = description

        return feedRepository.save(feed).toResponse()
    }

    @Transactional
    fun deleteFeed(feedId: Long) {
        // TODO : 유저 인증/인가
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        feedRepository.delete(feed)
    }
}