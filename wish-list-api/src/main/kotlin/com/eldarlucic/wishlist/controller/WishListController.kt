package com.eldarlucic.wishlist.controller

import com.eldarlucic.wishlist.domain.WishList
import com.eldarlucic.wishlist.exception.ErrorHandler
import com.eldarlucic.wishlist.service.WishListService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Controller("/wishlist")
open class WishListController(private val wishListService: WishListService): ErrorHandler() {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    open fun getAllUserWishLists(
        @QueryValue @NotBlank email: String,
        @QueryValue page: Int?,
        @QueryValue size: Int?
    ): List<WishList> {
        return wishListService.getAllWIshListsForUser(email, page ?: 0, size ?: 2)
    }
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    open fun saveWishList(@Body wishList: WishList): WishList {
        return wishListService.createWishList(wishList)
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getWishList(@PathVariable("id") wishListId: Long): WishList {
        return wishListService.getWishList(wishListId)
    }

    @Delete("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun deleteWishList(
        @PathVariable("id") wishListId: Long,
        @QueryValue @NotBlank userEmail: String
    ): Int {
        return wishListService.removeWishList(wishListId, userEmail)
    }

    @Put("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun deleteWishList(
        @PathVariable("id") wishListId: Long,
        @QueryValue @NotBlank userEmail: String,
        @Body wishList: WishList
    ): WishList {
        return wishListService.updateWishList(wishListId, wishList, userEmail)
    }
}