package com.teamsparta.mini5foodfeed.domain.feed.dto

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import java.time.LocalDateTime
import javax.swing.border.TitledBorder

data class FeedWithoutCommentResponse(
    val id: Long?,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val tagVo: TagVo,
    val imageUrl : String,
    val likedCount : Int
)
