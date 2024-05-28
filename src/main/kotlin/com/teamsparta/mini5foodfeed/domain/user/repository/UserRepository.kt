package com.teamsparta.mini5foodfeed.domain.user.repository

import com.teamsparta.mini5foodfeed.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User,Long> {
    fun existsByUserId(userId:String):Boolean
    fun existsByUserName(userName:String):Boolean
}