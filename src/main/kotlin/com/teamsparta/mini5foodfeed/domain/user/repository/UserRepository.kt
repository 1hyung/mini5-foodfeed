package com.teamsparta.mini5foodfeed.domain.user.repository

import com.teamsparta.mini5foodfeed.domain.user.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {
    fun existsByUserId(userId: String): Boolean
    fun findByUserId(userId: String): Users?
}