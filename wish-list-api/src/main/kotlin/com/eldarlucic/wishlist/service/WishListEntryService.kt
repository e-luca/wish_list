package com.eldarlucic.wishlist.service

import com.eldarlucic.wishlist.domain.WishListEntry
import com.eldarlucic.wishlist.repository.WishListEntryRepository
import com.eldarlucic.wishlist.repository.WishListRepository
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import java.sql.Timestamp
import java.util.*

@Singleton
class WishListEntryService(
    private val wishListEntryRepository: WishListEntryRepository,
    private val wishListRepository: WishListRepository
    ): WishListValidationService(wishListRepository, wishListEntryRepository) {
    fun createWishListEntry(wishListId: Long, wishListEntry: WishListEntry): WishListEntry {
        val wishList = checkIfWishListExists(wishListId)
        wishListEntry.wishListId = wishList.id
        wishListEntry.reference = wishList.ownerEmail
        wishListEntry.createdDate = wishListEntry.createdDate ?: Timestamp(Date().time)
        wishListEntry.updatedDate = wishListEntry.updatedDate ?: Timestamp(Date().time)

        checkIfNoteIsEmpty(wishListEntry.note)

        return wishListEntryRepository.save(wishListEntry)
    }

    fun getWishListEntry(listEntryId: Long): WishListEntry = wishListEntryRepository.getWishListEntry(listEntryId)

    fun getAllWishListEntries(wishListId: Long , page: Int, size: Int): List<WishListEntry> {
        checkIfWishListExists(wishListId)
        val pageable = Pageable.from(page, size)

        return wishListEntryRepository.getAllWishListEntries(wishListId, pageable)

    }

    fun updateWishListEntry(wishListId: Long, wishListEntry: WishListEntry, userEmail: String): WishListEntry {
        val wishList = checkIfWishListExists(wishListId)
        val returnedListEntry = checkIfListEntryExists(wishListEntry.id ?: 0)
        checkIfUserIsAbleToUpdateOrDelete(userEmail, wishList)
        checkIfNoteIsEmpty(wishListEntry.note)
        wishListEntry.createdDate = wishListEntry.createdDate ?: returnedListEntry.createdDate
        wishListEntry.updatedDate = Timestamp(Date().time)

        return wishListEntryRepository.update(wishListEntry)
    }

    fun deleteWishListEntry(wishListId: Long, entryId: Long, email: String): Int {
        val wishList = checkIfWishListExists(wishListId)
        checkIfListEntryExists(entryId)
        checkIfUserIsAbleToUpdateOrDelete(email, wishList)

        return wishListEntryRepository.deleteWishListEntry(entryId)
    }
}