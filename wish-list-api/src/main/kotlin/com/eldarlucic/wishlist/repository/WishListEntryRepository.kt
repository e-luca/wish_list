package com.eldarlucic.wishlist.repository

import com.eldarlucic.wishlist.domain.WishListEntry
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class WishListEntryRepository(): PageableRepository<WishListEntry, Long> {
    abstract fun save(wishListEntry: WishListEntry): WishListEntry

    abstract fun update(wishListEntry: WishListEntry): WishListEntry

    @Query("SELECT * FROM wish_list_entry wle WHERE wle.id = :listEntryId")
    abstract fun getWishListEntry(listEntryId: Long): WishListEntry

    @Query("SELECT * FROM wish_list_entry wle WHERE wle.wish_list_id = :wishListId")
    abstract fun getAllWishListEntries(wishListId: Long, pageable: Pageable): List<WishListEntry>

    @Query("DELETE FROM wish_list_entry wle WHERE wle.id = :listEntryId")
    abstract fun deleteWishListEntry(listEntryId: Long): Int

    @Query("DELETE FROM wish_list_entry wle WHERE wle.wish_list_id = :wishListId")
    abstract fun deleteAllWishListEntries(wishListId: Long)
}