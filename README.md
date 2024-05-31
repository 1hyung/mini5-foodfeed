# 푸드 뉴스 피드 [ FeeDo ]
##### E-Do 미니 프로젝트
- [기능소개](#기능소개)
- [API 명세서](#API명세서)
- [회의 기록](#회의기록)
-----------------------------------------------
### 기능소개
##### 유저 부분

```kotlin
@Entity
class Users(
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
    val comment: MutableList<Comment>? = null


) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    val userRole: List<UserRole>? = null

}
```
### User Entity

1. 로그인 : 
2. 회원가입 : 
3. 회원정보수정 :
4. 내 정보 보기 :
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
    var tag: Tag
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
4. 피드 목록 조회 : 최신순으로 정렬된 피드 목록을 받아옵니다. 조회시 태그 기준으로 필터링이 가능하며, 목록 반환시 해당 피드에 달린 가장 최근의 댓글 5개가 같이 반환됩니다. 게스트, 유저 모두 사용 가능합니다.
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
    val user: Users?
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


-------------------------------------------------


### API명세서
![API 명세서](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2F0e06600b-6487-460e-ae7b-eb8e9c8926f8%2FUntitled.png?table=block&id=52d5e878-fbe1-4c88-bf18-10d7e6da36b4&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=131562d9-a5ac-40fb-acae-5482c29c5c70&cache=v2)

--------------------------------------------------
### 회의기록
![회의 기록](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2F5d006667-401b-4334-8df3-dca8283bbab4%2F%25ED%259A%258C%25EC%259D%2598_%25EA%25B8%25B0%25EB%25A1%259D.jpg?table=block&id=59d4ed19-db3a-407c-b742-1d8170a98fb3&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=131562d9-a5ac-40fb-acae-5482c29c5c70&cache=v2)

=================================================================

### 개발환경 <br>
Language : Kotlin <br>
IDE : IntelliJ <br>
SDK : termurin-17 java version 17.0.11 <br>
JDK: Eclipse Adoptium\jdk-17.0.11.9-hotspot <br>
Framework : Spring Boot v.3.2.5 <br>
DB : Supabase | Postgres <br>
API TEST : Swagger 2.2.0, PostMan










------------------------






