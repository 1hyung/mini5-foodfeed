package com.teamsparta.mini5foodfeed.domain.feed.repository

import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<Feed, Long> {
}