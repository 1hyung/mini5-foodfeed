package com.teamsparta.mini5foodfeed.domain.feed.service

import com.teamsparta.mini5foodfeed.domain.feed.dto.*
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.model.toResponse
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FeedServiceImpl(
    private val feedRepository: FeedRepository
) : FeedService {

    override fun getFeedList(
        tags: List<String>?,
        author: String?,
        cursorRequest: CursorRequest
    ): CursorPage<FeedResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getFeedDetail(feedId: Long): FeedResponseDto {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NotFoundException()
        return feed.toResponse()
    }

    override fun createFeed(request: CreateRequestDto): FeedResponseDto {
        return feedRepository.save(
            Feed(
                title = request.title,
                description = request.description,
                createdAt = LocalDateTime.now(),
                tags = emptyList(),
                comments = emptyList(),
                user = user
            )
        ).toResponse()
    }

    override fun updateFeed(feedId : Long, request: UpdateRequestDto): FeedResponseDto {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NotFoundException()
        val (title, description) = request
            feed.title = title
            feed.description = description

        return feedRepository.save(feed).toResponse()
    }

    override fun deleteFeed(feedId: Long): ResponseEntity<Unit> {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NotFoundException()
        feedRepository.delete(feed)
    }
}