package com.teamsparta.mini5foodfeed.domain.feed.service

import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.dto.*
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.model.toResponse
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.domain.user.model.User
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
    commentRepository: CommentRepository
) {

    private final val commentRepository: CommentRepository = TODO("initialize me")

    fun getFeedList(
        tags: Tag?,
        cursor: Int
    ): CursorPageResponse {
        val pageable = PageRequest.of(0,20, Sort.Direction.DESC, "createdAt")
        val feedSlice : Slice<FeedResponse>  = feedRepository.findAllByCursorAndFilters(cursor, tags, pageable)
        val nextCursor = if (feedSlice.hasNext()) feedSlice.nextPageable().pageNumber else null

        return CursorPageResponse(feedSlice, nextCursor)
    }

    fun getFeedDetail(feedId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        return feed.toResponse()
    }

    @Transactional
    fun createFeed(request: CreateFeedRequest): FeedResponse {
        // val user: User = TODO : 인증,인가 과정에서 유저 찾아오고 밑에 save 에서 초기화된 이 유저를 저장
        return feedRepository.save(
            Feed(
                title = request.title,
                description = request.description,
                createdAt = LocalDateTime.now(),
                comments = null,
                user = User
            )
        ).toResponse()
    }

    @Transactional
    fun updateFeed(feedId : Long, request: UpdateFeedRequest): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        val (title, description) = request
            feed.title = title
            feed.description = description

        return feedRepository.save(feed).toResponse()
    }

    @Transactional
    fun deleteFeed(feedId: Long) {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        feedRepository.delete(feed)
    }

//    fun findAllWithComment() : Slice<FeedResponseWithComment> {
//        val findAllWithComment = feedRepository.findAllWithComment
//    }


//    private fun getCommentByFeed(feedId: Long, limit: Int): List<Comment> {
//        return commentRepository.findByFeedIdOrderByCreatedAtDesc(feedId).take(limit)
//    }
}