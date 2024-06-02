package com.teamsparta.mini5foodfeed.domain.comment.repository

import com.teamsparta.mini5foodfeed.common.status.OrderType
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.feed f WHERE f.id = :feedId ORDER BY c.createdAt DESC")
    fun findTop5ByFeedIdOrderByCreatedAtDesc(feedId: Long?, pageable: Pageable): List<Comment>

    fun findByFeedId(feedId: Long) : List<Comment>

    @Query("select c from Comment c where c.user = :user order by :order desc")
    fun findByUserOrderByParam(user: Users, order : OrderType, pageable: Pageable): List<Comment>
}