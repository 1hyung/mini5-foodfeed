package com.teamsparta.mini5foodfeed.domain.feed.comment.repository

import com.teamsparta.mini5foodfeed.domain.feed.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByFeedId(feedId: Long): List<Comment>
}