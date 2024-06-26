# 푸드 뉴스 피드 [ FeeDo ]
##### E-Do 미니 프로젝트
- [기능소개](#기능소개)
- [API 명세서](#API명세서)
- [회의기록](#%EF%B8%8F회의기록)
-----------------------------------------------
### 기능소개

##### 유저 부분

```kotlin
@Entity
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column
    var userId: String,
    @Column
    var userName: String,
    @Column
    var password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var feed: MutableList<Feed>? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val comment: MutableList<Comment>? = null,
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    val commentLike : MutableList<Comment>?,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    val feedLike: MutableList<FeedLike>?,

) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    val userRole: List<UserRole>? = null
}
```
### User Entity


1. 로그인 : 로그인 하지 않은(인증 되지 않은) 회원만 로그인 할 수 있습니다. / 로그인-> JWT 생성 후 반환 
2. 회원가입 : 로그인 하지 않은(인증 되지 않은) 회원만 회원 가입 할 수 있습니다. / 회원가입 -> 아이디 중복 검사 후 회원 가입
3. 내 정보 보기 : UserDetails정보 속 유저 아이디를 활용해 요청한 본인에 대한 정보를 반환받음
4. 내 정보 수정 : 인증 된 사용자에 아이디와 일치하는 DB 요소에 접근해 수정 
5. 내 피드 보기 : 자기가 작성한 피드를 모아 볼 수 있습니다. 좋아요 많은순, 최신순을 고를 수 있습니다.
6. 내 댓글 보기 : 자기가 작성한 댓글을 모아 볼 수 있습니다. 좋아요 많은순, 최신순을 고를 수 있습니다.

#### 피드 부분

```kotlin
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
    var imageUrl: String,
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", orphanRemoval = true)
    val feedLike : MutableList<FeedLike>?,

    @Column(nullable = false)
    var likedCount : Int = 0

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
```
### Feed Entity

1. 피드 작성 : 로그인한 회원만 피드를 작성 할 수 있습니다.
2. 피드 수정 : 피드를 작성한 본인이 로그인시에만 해당 피드를 수정 할 수 있습니다.
3. 피드 삭제 : 피드를 작성한 본인이 로그인시에만 해당 피드를 삭제 할 수 있습니다. 해당 피드에 달린 댓글들은 그 피드가 삭제되면 같이 삭제됩니다.
4. 피드 목록 조회 : 최신순으로 정렬된 피드 목록을 받아옵니다. 조회시 태그 기준으로 필터링이 가능하며, 목록 반환시 해당 피드에 달린 가장 최근의 댓글 3개가 같이 반환됩니다. 게스트, 유저 모두 사용 가능합니다.
5. 피드 개별 조회 : 해당 피드를 조회하며 그 피드의 모든 댓글도 같이 받아옵니다. 게스트, 유저 모두 사용이 가능합니다.

#### 댓글 부분

```kotlin
@Entity
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,


    @Column(nullable = false)
    var contents: String,


    @JoinColumn(name = "feed_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val feed: Feed,


    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_user_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    val user: Users?,
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", orphanRemoval = true)
    val commentLike : MutableList<CommentLike>?,

    @Column(nullable = false)
    var likedCount : Int = 0
)
```
### Comment Entity

1. 댓글 작성 : 로그인한 회원만 각 피드에 댓글을 작성할 수 있습니다.
2. 댓글 수정 : 해당 댓글을 작성한 사용자만 댓글을 수정할 수 있습니다.
3. 댓글 삭제 : 해당 댓글을 작성한 사용자만 댓글을 삭제 할 수 있습니다.
#### 태그 부분

```kotlin
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
```
### Tag Entity


* Feed 작성/ 수정 시 tag 정보를 함께 입력할 수 있고, 저장된 tag 이름으로 필터링하여 조회할 수 있습니다.
* QueryDSL 을 사용한 동적쿼리로 조회 시 필터링 기능을 구현했습니다.



### 좋아요 부분

``kotlin
@Entity
data class FeedLike(

    @JoinColumn(name = "feed_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val feed : Feed,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user : Users,

    val likedTime: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}
```

``kotlin
@Entity
data class CommentLike(

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val comment : Comment,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user : Users
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
```

### Like Entities

1. 좋아요 누르기 : 피드와 댓글에 좋아요를 누를 수 있습니다. 이미 좋아요 된 상태라면 좋아요가 취소됩니다
좋아요 숫자는 따로 좋아요 테이블의 숫자를 세지 않고 해당 feed,comment 쪽의 필드에 따로 숫자를 저장하는 필드와 로직을 줘서 쿼리문을 최소화했습니다
2. 좋아요 랭킹 : 24시간 이내에 좋아요를 가장 많이 받은 피드 5개를 볼 수 있습니다

-------------------------------------------------


### 📝API명세서, ERD

![API 명세서](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbDSDMf%2FbtsHLxO8BVu%2FGkvqoHacntC2WnC6As8TWK%2Fimg.jpg)


![ERD](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbRd77b%2FbtsHMe9klz9%2FEiOezbSG4ao5tHscMRxK11%2Fimg.png)

--------------------------------------------------

### ✏️회의기록

![회의 기록](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F12swM%2FbtsHLGkUOE8%2FukL1FUxIy0iKfeBR2peob1%2Fimg.png)

=================================================================

### 🔧개발환경 <br>
Language : Kotlin <br>
IDE : IntelliJ <br>
SDK : termurin-17 java version 17.0.11 <br>
JDK: Eclipse Adoptium\jdk-17.0.11.9-hotspot <br>
Framework : Spring Boot v.3.2.5 <br>
DB : Supabase | Postgres <br>
API TEST : Swagger 2.2.0, PostMan










------------------------






