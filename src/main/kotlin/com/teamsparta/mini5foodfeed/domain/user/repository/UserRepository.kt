package com.teamsparta.mini5foodfeed.domain.user.repository

import com.teamsparta.mini5foodfeed.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User,Long> {
}