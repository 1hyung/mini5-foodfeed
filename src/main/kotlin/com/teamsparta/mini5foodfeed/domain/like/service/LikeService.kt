package com.teamsparta.mini5foodfeed.domain.like.service

import com.teamsparta.mini5foodfeed.common.exception.ModelNotFoundException
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedWithoutCommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.model.toResponseWithoutComment
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.domain.like.model.toggleCommentLike
import com.teamsparta.mini5foodfeed.domain.like.model.toggleFeedLike
import com.teamsparta.mini5foodfeed.domain.like.repository.CommentLikeRepository
import com.teamsparta.mini5foodfeed.domain.like.repository.FeedLikeRepository
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class LikeService(
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository,
    private val feedLikeRepository: FeedLikeRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun toggleLikeFeed(feedId : Long, userId: Long) : Unit {
        val feed = findFeed(feedId)
        val user: Users = findUser(userId)

        val existingLike = feedLikeRepository.findByFeedAndUser(feed, user)
        return if (existingLike != null) {
            feedLikeRepository.delete(existingLike)
            feed.likedCount --
            println("disliked")
        } else {
            toggleFeedLike(feed, user)
            feed.likedCount ++
            println("liked")
        }
    }


    @Transactional
    fun toggleLikeComment(feedId : Long, commentId : Long, userId: Long) : Unit {
        findFeed(feedId)
        val comment = findComment(commentId)
        val user = findUser(userId)

        val existingLike = commentLikeRepository.findByCommentAndUser(comment, user)
        if (existingLike != null) {
            commentLikeRepository.delete(existingLike)
            comment.likedCount --
            println("disliked")
        } else {
            toggleCommentLike(comment, user)
            comment.likedCount ++
            println("liked")
        }
    }

    fun getTop5LikedFeedIn24Hours(): List<FeedWithoutCommentResponse> {
        val day = LocalDateTime.now().minusDays(1)
        // day 부분을 파람으로 시간 형태로 받아온 후 밑의 레포쪽의 파람으로 주면
        //  받아온 시간 이내의 탑5 좋아요 피드를 가져오는 로직으로 변경 가능

        val pageable = PageRequest.of(0,5)
        val top5Feeds = feedLikeRepository.findTodayFeeds(day, pageable)

        return top5Feeds.map{it.toResponseWithoutComment()}
    }



    private fun findFeed(feedId : Long) : Feed {
        return feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
    }

    private fun findComment(commentId : Long) : Comment {
        return commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)
    }

    private fun findUser(userId : Long) : Users {
        return userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("user", userId)
    }
}

