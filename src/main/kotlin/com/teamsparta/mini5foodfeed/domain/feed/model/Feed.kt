package com.teamsparta.mini5foodfeed.domain.feed.model

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedWithoutCommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.dto.TagVo
import com.teamsparta.mini5foodfeed.domain.like.dto.LikeResponse
import com.teamsparta.mini5foodfeed.domain.like.model.FeedLike
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import jakarta.persistence.*
import java.time.LocalDateTime


@Table(name = "feed")
@Entity
data class Feed(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "feed")
    val comments: MutableList<Comment>? ,

    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_user_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    val user: Users?,

    @OneToOne
    @JoinColumn(name = "tag_id")
    var tag: Tag,

    @Column(name = "image_url")
    var imageUrl: String?,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", orphanRemoval = true)
    val feedLike : MutableList<FeedLike>?,

    @Column(nullable = false)
    var likedCount : Int = 0

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}


fun Feed.toResponse(): FeedResponse {
    val commentResponses = this.comments?.map { it -> CommentResponse(commentId = it.id,contents = it.contents, createdAt = it.createdAt, likedCount = it.likedCount) } ?: emptyList()
    return FeedResponse(
        id = id!!,
        title = title,
        description = description,
        createdAt = createdAt,
        comments = commentResponses,
        tagVo = tag.toVo(),
        imageUrl = imageUrl,
        likedCount = likedCount
    )
}

fun Feed.toResponseWithoutComment(): FeedWithoutCommentResponse {
    return FeedWithoutCommentResponse(
        id = id!!,
        title = title,
        description = description,
        createdAt = createdAt,
        tagVo = tag.toVo(),
        imageUrl = imageUrl,
        likedCount = likedCount
    )
}

fun Feed.updateTag(tagVo: TagVo){
    this.tag.sweet = tagVo.sweet
    this.tag.hot = tagVo.hot
    this.tag.spicy = tagVo.spicy
    this.tag.cool = tagVo.cool
    this.tag.sweetMood = tagVo.sweetMood
    this.tag.dateCourse = tagVo.dateCourse
}

fun Feed.toLikeResponse(): LikeResponse {
    return LikeResponse(
        likedCount = this.likedCount
    )
}