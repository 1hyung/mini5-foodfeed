package com.teamsparta.mini5foodfeed.domain.like.controller

import com.teamsparta.mini5foodfeed.common.dto.CustomUser
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedResponse
import com.teamsparta.mini5foodfeed.domain.like.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/feed/{feedId}")
class LikeController(
    private val likeService: LikeService,
) {

    @PatchMapping("/like")
    fun toggleLikeFeed(
        @PathVariable feedId: Long,
    ) : ResponseEntity<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(likeService.toggleLikeFeed(feedId,userId))
    }

    @PatchMapping("/{commentId}/like")
    fun toggleLikeComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
    ) : ResponseEntity<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(likeService.toggleLikeComment(feedId, commentId, userId))
    }

}