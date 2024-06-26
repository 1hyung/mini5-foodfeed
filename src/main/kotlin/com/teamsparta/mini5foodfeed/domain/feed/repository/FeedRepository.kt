package com.teamsparta.mini5foodfeed.domain.feed.repository

import com.teamsparta.mini5foodfeed.common.status.OrderType
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long> {

    @Query("select f from Feed f where (:cursorId is null or f.id > :cursorId) order by f.createdAt desc")
    fun findAllByCursor(
        @Param("cursorId") cursor: Int?,
        pageable: Pageable,
    ): Slice<Feed>


    @Query("select f from Feed f where :user = f.user order by :order desc")
    fun findByUserOrderByParam(user : Users, order : OrderType, pageable: Pageable) : List<Feed>
}