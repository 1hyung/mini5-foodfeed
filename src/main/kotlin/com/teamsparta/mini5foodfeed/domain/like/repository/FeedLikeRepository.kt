package com.teamsparta.mini5foodfeed.domain.like.repository

import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.like.model.FeedLike
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface FeedLikeRepository: JpaRepository<FeedLike, Long> {

    fun findByFeedAndUser(feed: Feed, user: Users): FeedLike?

    @Query("select f.feed from FeedLike f where f.likedTime >= :day group by f.feed order by count(f.feed) desc limit 5")
    fun findTodayFeeds(day:LocalDateTime, pageable : Pageable) : Page<Feed>

    fun findByFeed(feed: Feed) : List<FeedLike>?

}