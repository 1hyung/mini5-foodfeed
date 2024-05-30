package com.teamsparta.mini5foodfeed.domain.feed.controller

import com.teamsparta.mini5foodfeed.domain.feed.dto.CreateFeedRequest
import com.teamsparta.mini5foodfeed.domain.feed.dto.CursorPageResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.UpdateFeedRequest
import com.teamsparta.mini5foodfeed.domain.feed.service.FeedService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/feeds")
@RestController
class FeedController(
    private val feedService: FeedService
) {

    @GetMapping("/cursor")
    fun getFeedList(
        @RequestParam(required = false) cursor: Int = 0,
        @RequestParam(defaultValue = "20") size: Int,
    ): ResponseEntity<CursorPageResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedList(cursor))
    }


    @GetMapping("/{feedId}")
    fun getFeedDetails(
        @PathVariable("feedId") feedId : Long,
    ) : ResponseEntity<FeedResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedDetail(feedId))
    }

    @PostMapping
    fun createFeed(
        @RequestBody feedRequest: CreateFeedRequest,
    ) : ResponseEntity<FeedResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(feedService.createFeed(feedRequest))
    }

    @PutMapping("/{feedId}")
    fun updateFeed(
        @PathVariable("feedId") feedId: Long,
        @RequestBody request: UpdateFeedRequest
    ) : ResponseEntity<FeedResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.updateFeed(feedId, request))
    }

    @DeleteMapping("/{feedId}")
    fun deleteFeed(
        @PathVariable("feedId") feedId: Long,
    ) : ResponseEntity<Unit> {
        feedService.deleteFeed(feedId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
