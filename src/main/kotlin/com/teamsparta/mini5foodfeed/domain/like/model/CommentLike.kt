package com.teamsparta.mini5foodfeed.domain.like.model

import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import jakarta.persistence.*

@Entity
data class CommentLike(

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val comment : Comment,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user : Users
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun toggleCommentLike (comment: Comment, user: Users) {
    CommentLike(
        comment = comment,
        user = user
    )
}
