package com.teamsparta.mini5foodfeed.domain.feed.controller

import com.teamsparta.mini5foodfeed.common.dto.CustomUser
import com.teamsparta.mini5foodfeed.domain.feed.dto.*
import com.teamsparta.mini5foodfeed.domain.feed.service.FeedService
import com.teamsparta.mini5foodfeed.domain.like.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RequestMapping("/feeds")
@RestController
class FeedController(
    private val feedService: FeedService,
    private val likeService: LikeService
) {

    @GetMapping("/cursor")
    fun getFeedList(
        @RequestParam(required = false) cursor: Int = 0,
        @RequestParam(defaultValue = "20") size: Int,
        @ModelAttribute tagVo: TagVo
    ): ResponseEntity<CursorPageResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedList(cursor, tagVo))
    }


    @GetMapping("/{feedId}")
    fun getFeedDetails(
        @PathVariable("feedId") feedId : Long,
    ) : ResponseEntity<FeedResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedDetail(feedId))
    }


    @GetMapping("/popular")
    fun getPopularFeeds() : ResponseEntity<List<FeedResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(likeService.getTop5LikedFeedIn24Hours())
    }

    @PostMapping
    fun createFeed(
        @RequestBody feedRequest: CreateFeedRequest,
    ) : ResponseEntity<FeedResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(feedService.createFeed(feedRequest, userId))
    }

    @PutMapping("/{feedId}")
    fun updateFeed(
        @PathVariable("feedId") feedId: Long,
        @RequestBody request: UpdateFeedRequest
    ) : ResponseEntity<FeedResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.updateFeed(feedId, request,userId))
    }

    @DeleteMapping("/{feedId}")
    fun deleteFeed(
        @PathVariable("feedId") feedId: Long,
    ) : ResponseEntity<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        feedService.deleteFeed(feedId, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
