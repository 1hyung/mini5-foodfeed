package com.teamsparta.mini5foodfeed.domain.feed.repository

import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FeedRepository : JpaRepository<Feed, Long> {

    @Query("select f from Feed f where (:cursorId is null or f.id > :cursorId) and (:tags is null or f.tags in :tags) order by f.createdAt desc")
    fun findAllByCursorAndFilters(
        @Param("cursorId") cursor: Long?,
        @Param("tags") tags: List<String>?,
        pageable: Pageable
    )

}