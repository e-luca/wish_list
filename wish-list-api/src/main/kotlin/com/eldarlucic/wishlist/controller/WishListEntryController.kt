package com.eldarlucic.wishlist.controller

import com.eldarlucic.wishlist.domain.WishListEntry
import com.eldarlucic.wishlist.exception.ErrorHandler
import com.eldarlucic.wishlist.service.WishListEntryService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank

@Validated
@Controller("/wishlist/{wishListId}/entry")
open class WishListEntryController(private val wishListEntryService: WishListEntryService): ErrorHandler() {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    open fun getAllEntries(
        @PathVariable wishListId: Long,
        @QueryValue page: Int?,
        @QueryValue size: Int?
    ): List<WishListEntry> {
        return wishListEntryService.getAllWishListEntries(wishListId, page ?: 0, size ?: 2)
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    open fun createWishListEntry(
        @PathVariable("wishListId") id: Long,
        @Body entry: WishListEntry
    ): WishListEntry {
        return wishListEntryService.createWishListEntry(id, entry)
    }

    @Put
    @Produces(MediaType.APPLICATION_JSON)
    open fun updateWishListEntry(
        @PathVariable("wishListId") id: Long,
        @QueryValue @NotBlank userEmail: String,
        @Body entry: WishListEntry
    ): WishListEntry {
        return wishListEntryService.updateWishListEntry(id, entry, userEmail)
    }

    @Delete
    @Produces(MediaType.APPLICATION_JSON)
    open fun deleteWishListEntry(
        @PathVariable("wishListId") wishListId: Long,
        @QueryValue @NotBlank entryId: Long,
        @QueryValue @NotBlank userEmail: String
    ): Int {
        return wishListEntryService.deleteWishListEntry(wishListId, entryId, userEmail)
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getWishListEntry(
        @PathVariable("wishListId") wishListId: Long,
        @PathVariable("id") entryId: Long
    ): WishListEntry {
        return wishListEntryService.getWishListEntry(entryId)
    }
}