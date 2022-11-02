package com.eldarlucic.wishlist.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.sql.Timestamp

@Entity
data class WishListEntry(
    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    var id: Long?,
    @Column(name = "amount", nullable = true)
    var amount: Int?,
    @Column(name = "note", nullable = false)
    var note: String,
    @Column(name = "reference", nullable = false, length = 150)
    var reference: String?,
    @Column(name = "wish_list_id", nullable = false)
    var wishListId: Long?,
    @Column(name = "created_date", nullable = false)
    var createdDate: Timestamp?,
    @Column(name = "updated_date", nullable = false)
    var updatedDate: Timestamp?
)