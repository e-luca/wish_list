package com.eldarlucic.wishlist.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.sql.Timestamp

@Entity
data class WishList(
    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    var id: Long?,
    @Column(name = "name", nullable = false, length = 75)
    var name: String,
    @Column(name = "note", nullable = true)
    var note: String?,
    @Column(name = "owner_email", nullable = false, length = 150)
    var ownerEmail: String,
    @Column(name = "created_date", nullable = false)
    var createdDate: Timestamp?,
    @Column(name = "updated_date", nullable = false)
    var updatedDate: Timestamp?,
    @OneToMany(mappedBy = "wish_list_id")
    var entries: List<WishListEntry>?
)