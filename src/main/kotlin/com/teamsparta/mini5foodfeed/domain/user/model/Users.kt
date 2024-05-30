package com.teamsparta.mini5foodfeed.domain.user.model


import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class Users(
    @Column(name = "user_id")
    var userId: String,

    @Column(name = "user_name")
    var userName: String,

    @Column(name = "password")
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Users.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        userId = userId,
        userName = userName
    )
}