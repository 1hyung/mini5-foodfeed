package com.teamsparta.mini5foodfeed.domain.feed.comment.model

import jakarta.persistence.*

@Entity
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val feedId: Long,

    @Column(nullable = false)
    var contents: String
)
