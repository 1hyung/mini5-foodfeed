package com.teamsparta.mini5foodfeed.domain.user.model

import com.teamsparta.mini5foodfeed.common.status.ROLE
import jakarta.persistence.*

@Entity
class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_user_id"))
    val users: Users
)