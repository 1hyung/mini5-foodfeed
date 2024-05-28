package com.teamsparta.mini5foodfeed.domain.feed.controller

import com.teamsparta.mini5foodfeed.domain.feed.dto.*
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
        @RequestParam(required = false) tags: List<String>?,
        @RequestParam(required = false) author: String?,
        @RequestParam cursorRequest: CursorRequest,
        @RequestParam(required = false, defaultValue = "20") size: Int,
    ): ResponseEntity<CursorPage<FeedResponseDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedList(tags, author, cursorRequest))
    }


    @GetMapping("/{feedId}")
    fun getFeedDetails(
        @PathVariable("feedId") feedId : Long,
    ) : ResponseEntity<FeedResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedDetail(feedId))
    }

    @PostMapping
    fun createFeed(
        @RequestBody request: CreateRequestDto
    ) : ResponseEntity<FeedResponseDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(feedService.createFeed(request))
    }

    @PutMapping("/{feedId}")
    fun updateFeed(
        @PathVariable("feedId") feedId: Long,
        @RequestBody request: UpdateRequestDto
    ) : ResponseEntity<FeedResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.updateFeed(request))
    }

    @DeleteMapping("/{feedId}")
    fun deleteFeed(
        @PathVariable("feedId") feedId: Long,
    ) : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}