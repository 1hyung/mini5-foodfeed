package com.teamsparta.mini5foodfeed.domain.feed.repository

import com.querydsl.core.BooleanBuilder
import com.teamsparta.mini5foodfeed.domain.feed.model.QFeed.feed
import com.teamsparta.mini5foodfeed.domain.feed.model.QTag.tag
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import com.teamsparta.mini5foodfeed.domain.feed.dto.TagVo
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.model.QTag
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
class FeedRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory) {

    fun findAllByTagWithCursor(
        tagVo: TagVo,
        @Param("cursorId") cursor: Int?,
        pageable: Pageable,
    ): Slice<Feed> {

        val query = jpaQueryFactory.selectFrom(feed)
            .leftJoin(feed.tag, tag).fetchJoin()
            .where(
                cursor?.let { feed.id.gt(it) } ?: feed.id.isNotNull
            )
            .where(buildTagConditions(tagVo, tag))
            .orderBy(feed.createdAt.desc())

        val feeds = query.offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return SliceImpl(feeds, pageable, feeds.size.toLong() == pageable.pageSize.toLong())
    }

    private fun buildTagConditions(tagVo: TagVo, tag: QTag): BooleanBuilder {
        val booleanBuilder = BooleanBuilder()

        val conditions = mapOf(
            tagVo.sweet to tag.sweet,
            tagVo.hot to tag.hot,
            tagVo.spicy to tag.spicy,
            tagVo.cool to tag.cool,
            tagVo.sweetMood to tag.sweetMood,
            tagVo.dateCourse to tag.dateCourse
        )

        conditions.forEach { (condition, path) ->
            if (condition) {
                booleanBuilder.and(path.isTrue)
            }
        }

        return booleanBuilder
    }
}