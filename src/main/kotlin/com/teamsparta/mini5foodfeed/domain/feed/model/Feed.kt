package com.teamsparta.mini5foodfeed.domain.feed.model

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedResponse
import com.teamsparta.mini5foodfeed.domain.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime


@Table(name = "feed")
@Entity
data class Feed(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    val comments: MutableList<Comment>?,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @OneToOne
    @JoinColumn(name = "tag_id")
    var tag: Tag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}


fun Feed.toResponse(): FeedResponse {
    val commentResponses = this.comments?.map { it -> CommentResponse(contents = it.contents, createdAt = it.createdAt) }
    return FeedResponse(
        id = id!!,
        title = title,
        description = description,
        createdAt = createdAt,
        comments = commentResponses,
        user = user
    )
}
