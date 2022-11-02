package com.eldarlucic.wishlist.service

import com.eldarlucic.wishlist.domain.WishList
import com.eldarlucic.wishlist.domain.WishListEntry
import com.eldarlucic.wishlist.repository.WishListEntryRepository
import com.eldarlucic.wishlist.repository.WishListRepository
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import java.lang.Exception

open class WishListValidationService(
    private val wishListRepository: WishListRepository,
    private val wishListEntryRepository: WishListEntryRepository
) {
    fun checkIfWishListExists(wishListId: Long): WishList {
        try {
            return wishListRepository.getWishList(wishListId)
        } catch(exception: Exception) {
            throw HttpStatusException(HttpStatus.BAD_REQUEST, "Wish list with id: ${wishListId} does not exist")
        }
    }

    fun checkIfNoteIsEmpty(wishListEntryNote: String) {
        if (wishListEntryNote.isEmpty()) throw HttpStatusException(HttpStatus.BAD_REQUEST, "Entry note should not be empty!")
    }

    fun checkIfListEntryExists(listEntryId: Long): WishListEntry {
        try {
            return wishListEntryRepository.getWishListEntry(listEntryId)
        } catch (exception: Exception) {
            throw HttpStatusException(HttpStatus.BAD_REQUEST, "Wish list entry with id: ${listEntryId} does not exist")
        }
    }

    fun checkIfUserIsAbleToUpdateOrDelete(email: String, wishList: WishList) {
        if (!(wishList.ownerEmail.equals(email))) throw HttpStatusException(HttpStatus.UNAUTHORIZED, "User is not allowed to change entry!")
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"
        return emailRegex.toRegex().matches(email)
    }

    fun checkIfEmailIsValid(ownerEmail: String) {
        if (!isEmailValid(ownerEmail)) throw HttpStatusException(HttpStatus.BAD_REQUEST, "Email address is not valid!")
    }
}