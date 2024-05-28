package com.teamsparta.mini5foodfeed.domain.feed.model

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

    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val comments: MutableList<Comment>?,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}



fun Feed.toResponse() : FeedResponse {
    return FeedResponse(
        id = id!!,
        title = title,
        description = description,
        createdAt = createdAt,
        comments = comments,
        user = user
    )
}