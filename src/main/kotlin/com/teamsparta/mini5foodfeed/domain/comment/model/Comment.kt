package com.teamsparta.mini5foodfeed.domain.comment.model

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,


    @Column(nullable = false)
    var contents: String,


    @JoinColumn(name = "feed_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val feed: Feed,


    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_user_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    val user: Users?
)

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        commentId = this.id,
        contents = this.contents,
        createdAt = this.createdAt
    )
}