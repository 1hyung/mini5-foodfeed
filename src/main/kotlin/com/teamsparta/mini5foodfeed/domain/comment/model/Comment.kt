package com.teamsparta.mini5foodfeed.domain.comment.model

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

//    @Column(nullable = false)
//    val feedId: Long,
//    피드와의 관계성을 직접 맺기 위해 feedId 를 통한 매칭이 아닌 댓글 엔티티에서 피드를 가지도록 수정했습니다
//    feedId 를 사용할시 해당 댓글마다 feedId 를 가진 feed 를 가져오는 로직이 필요할 듯 해서 수정했습니다

    @Column(nullable = false)
    var contents: String,


    // 코맨트와 피드의 관계성을 맺기 위한 feed 생성자
    @JoinColumn(name = "feed_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    val feed : Feed,


    val createdAt : LocalDateTime
) {

}

// comment 저장시 직접 설정하고 설정된 코맨트를 response 형태로 가져오기위한 외부 함수
fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        contents = contents,
    )
}
