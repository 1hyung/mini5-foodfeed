package com.teamsparta.mini5foodfeed.domain.feed.service

import com.teamsparta.mini5foodfeed.domain.feed.dto.*
import org.springframework.http.ResponseEntity

interface FeedService {

    fun getFeedList(tags: List<String>?, author: String?, cursorRequest: CursorRequest) : CursorPage<FeedResponseDto>

    fun getFeedDetail(feedId: Long) : FeedResponseDto

    fun createFeed(request: CreateRequestDto) : FeedResponseDto

    fun updateFeed(feedId: Long, request: UpdateRequestDto) : FeedResponseDto

    fun deleteFeed(feedId: Long) : ResponseEntity<Unit>

}