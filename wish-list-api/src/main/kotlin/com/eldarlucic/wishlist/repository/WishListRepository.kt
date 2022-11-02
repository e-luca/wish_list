package com.eldarlucic.wishlist.repository

import com.eldarlucic.wishlist.domain.WishList
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class WishListRepository() : PageableRepository<WishList, Long> {
    abstract fun save(wishList: WishList): WishList

    abstract fun update(wishList: WishList): WishList

    @Query("SELECT * FROM wish_list wl WHERE wl.id = :wishListId")
    abstract fun getWishList(wishListId: Long): WishList

    @Query("SELECT * FROM wish_list wl WHERE wl.owner_email = :email")
    abstract fun getAllWishListForUser(email: String, pageable: Pageable): List<WishList>

    @Query("DELETE FROM wish_list wl WHERE wl.id = :wishListId")
    abstract fun removeWishList(wishListId: Long): Int
}
