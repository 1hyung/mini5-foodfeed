package com.teamsparta.mini5foodfeed.domain.comment.repository

import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

    fun findTop5ByFeedIdOrderByCreatedAtDesc(feedId: Long): List<Comment>
}