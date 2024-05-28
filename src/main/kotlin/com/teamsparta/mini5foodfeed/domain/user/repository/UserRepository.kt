package com.teamsparta.mini5foodfeed.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User,Long> {
}