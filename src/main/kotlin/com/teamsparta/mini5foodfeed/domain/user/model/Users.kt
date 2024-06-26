package com.teamsparta.mini5foodfeed.domain.user.model

import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.like.model.FeedLike
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import jakarta.persistence.*


@Entity
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column
    var userId: String,
    @Column
    var userName: String,
    @Column
    var password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var feed: MutableList<Feed>? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val comment: MutableList<Comment>? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    val commentLike : MutableList<Comment>?,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    val feedLike: MutableList<FeedLike>?,

) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    val userRole: List<UserRole>? = null
}

fun Users.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        userId = userId,
        userName = userName
    )
}