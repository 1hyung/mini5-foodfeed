package com.teamsparta.mini5foodfeed.domain.user.model

import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Users(
    @Column
    var userId: String,
    @Column
    var userName: String,
    @Column
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
// test