package com.teamsparta.mini5foodfeed.domain.comment.model

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.like.model.CommentLike
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
    val user: Users?,

    //여기서부터는 좋아요 관련해서 작성해본 필드
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", orphanRemoval = true)
    val commentLike : MutableList<CommentLike>?,

    @Column(nullable = false)
    var likedCount : Int = 0
    //여기까지
)

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        commentId = this.id,
        contents = this.contents,
        createdAt = this.createdAt,
        likedCount = this.likedCount,
    )
}