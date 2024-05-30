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
    var feed: Feed? = null
}

fun Tag.toVo() : TagVo {
    return TagVo(
        sweet = this.sweet,
        hot = this.hot,
        spicy = this.spicy,
        cool = this.cool,
        sweetMood = this.sweetMood,
        dateCourse = this.dateCourse
    )
}