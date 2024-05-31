package com.teamsparta.mini5foodfeed.domain.comment.repository

import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.feed f WHERE f.id = :feedId ORDER BY c.createdAt DESC")
    fun findTop5ByFeedIdOrderByCreatedAtDesc(feedId: Long?, pageable: Pageable): List<Comment>

    fun findByFeedId(feedId: Long) : List<Comment>
}