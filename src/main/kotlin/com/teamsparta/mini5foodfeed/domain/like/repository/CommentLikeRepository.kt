package com.teamsparta.mini5foodfeed.domain.like.repository

import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.like.model.CommentLike
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, Long> {

    fun findByCommentAndUser(comment: Comment, user: Users): CommentLike?
}