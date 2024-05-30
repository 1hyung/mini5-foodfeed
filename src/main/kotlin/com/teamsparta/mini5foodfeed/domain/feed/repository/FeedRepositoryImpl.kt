package com.teamsparta.mini5foodfeed.domain.feed.repository

import com.teamsparta.mini5foodfeed.domain.feed.model.QFeed.feed
import com.teamsparta.mini5foodfeed.domain.feed.model.QTag.tag
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.query.Param

class FeedRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val tagRepository: TagRepository) : FeedRepository {

    override fun findAllByCursor(
        @Param("cursorId") cursor: Int?,
        pageable: Pageable,
    ): Slice<Feed> {

        val query = JPAQuery<Feed>()

        return jpaQueryFactory.selectFrom(feed)
    }
}