package com.teamsparta.mini5foodfeed.domain.like.model

import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class FeedLike(

    @JoinColumn(name = "feed_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val feed : Feed,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user : Users,

    val likedTime: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}

fun toggleFeedLike (feed: Feed, user: Users): FeedLike {
    val newLike =
    FeedLike(
        feed = feed,
        user = user
    )
    return newLike
}