package com.teamsparta.mini5foodfeed.domain.feed.repository

import com.teamsparta.mini5foodfeed.domain.feed.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository: JpaRepository<Tag, Long> {
interface TagRepository {
}