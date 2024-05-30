package com.teamsparta.mini5foodfeed.domain.user.repository

import com.teamsparta.mini5foodfeed.domain.user.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRole, Long>