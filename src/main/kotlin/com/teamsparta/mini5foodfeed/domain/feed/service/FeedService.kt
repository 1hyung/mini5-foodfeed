package com.teamsparta.mini5foodfeed.domain.feed.service


import com.teamsparta.mini5foodfeed.common.exception.ModelNotFoundException
import com.teamsparta.mini5foodfeed.common.exception.NotAuthenticationException
import com.teamsparta.mini5foodfeed.common.status.OrderType
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.dto.*
import com.teamsparta.mini5foodfeed.domain.feed.model.*
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepositoryImpl
import com.teamsparta.mini5foodfeed.domain.feed.repository.TagRepository
import com.teamsparta.mini5foodfeed.domain.like.repository.FeedLikeRepository
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
    private val feedLikeRepository: FeedLikeRepository,
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
                .map { comment -> CommentResponse(comment.id, comment.contents, comment.createdAt, likedCount = feed.likedCount)}
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
                tag =tag,
                imageUrl = feedRequest.imageUrl,
                feedLike = null,
                likedCount = 0
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
        feed.imageUrl = request.imageUrl
        feed.updateTag(request.tagVo)

        return feed.toResponse()
    }

    @Transactional
    fun deleteFeed(feedId: Long, userId: Long) {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        if (userId != feed.user!!.id)  throw NotAuthenticationException("feed")
        tagRepository.delete(feed.tag)
        feedRepository.delete(feed)
        val comments = commentRepository.findByFeedId(feedId)
        commentRepository.deleteAll(comments)
        val feedLike = feedLikeRepository.findByFeed(feed)
        if (feedLike != null) {
            feedLikeRepository.deleteAll(feedLike)
        }
    }

    fun getMyFeeds(userId: Long, order : OrderType, page : Int): List<FeedWithoutCommentResponse> {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("user", userId)
        val pageRequest = PageRequest.of(page,10)
        val feeds = feedRepository.findByUserOrderByParam(user, order, pageRequest).content
        return feeds.map{it.toResponseWithoutComment()}
    }

}