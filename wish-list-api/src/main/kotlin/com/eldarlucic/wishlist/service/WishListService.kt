package com.eldarlucic.wishlist.service

import com.eldarlucic.wishlist.domain.WishList
import com.eldarlucic.wishlist.repository.WishListEntryRepository
import com.eldarlucic.wishlist.repository.WishListRepository
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import java.sql.Timestamp
import java.util.*

@Singleton
class WishListService(
    private val wishListRepository: WishListRepository,
    private val wishListEntryRepository: WishListEntryRepository
): WishListValidationService(wishListRepository, wishListEntryRepository) {
    fun createWishList(wishList: WishList): WishList {
        checkIfEmailIsValid(wishList.ownerEmail)
        wishList.createdDate = wishList.createdDate ?: Timestamp(Date().time)
        wishList.updatedDate = wishList.updatedDate ?: Timestamp(Date().time)

        return wishListRepository.save(wishList)
    }

    fun getWishList(wishListId: Long): WishList {
        val wishList = wishListRepository.getWishList(wishListId)
        val pageable = Pageable.from(0, 20)
        val wishListEntries = wishListEntryRepository.getAllWishListEntries(wishListId, pageable)
        wishList.entries = wishListEntries

        return wishList
    }

    fun getAllWIshListsForUser(email: String, page: Int, size: Int): List<WishList> {
        val pageable = Pageable.from(page, size)
        return wishListRepository.getAllWishListForUser(email, pageable)
    }

    fun removeWishList(wishListId: Long, userEmail: String): Int {
        val wishList = checkIfWishListExists(wishListId)
        checkIfUserIsAbleToUpdateOrDelete(userEmail, wishList)
        wishListEntryRepository.deleteAllWishListEntries(wishListId)

        return wishListRepository.removeWishList(wishListId)
    }

    fun updateWishList(wishListId: Long, wishList: WishList, userEmail: String): WishList {
        val returnedList = checkIfWishListExists(wishListId)
        checkIfEmailIsValid(wishList.ownerEmail)
        checkIfUserIsAbleToUpdateOrDelete(userEmail, returnedList)
        wishList.createdDate = wishList.createdDate ?: returnedList.createdDate
        wishList.updatedDate = Timestamp(Date().time)

        return wishListRepository.update(wishList)
    }
}