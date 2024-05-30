package com.teamsparta.mini5foodfeed.domain.feed.model

import com.teamsparta.mini5foodfeed.domain.feed.dto.TagVo
import jakarta.persistence.*

@Entity
class Tag(
    var sweet: Boolean,
    var hot: Boolean,
    var spicy: Boolean,
    var cool: Boolean,
    var sweetMood: Boolean,
    var dateCourse: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne
    lateinit var feed: Feed
}