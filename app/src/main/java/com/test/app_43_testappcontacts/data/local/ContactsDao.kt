package com.test.app_43_testappcontacts.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.app_43_testappcontacts.data.Contact

@Dao
interface ContactsDao {
    @Transaction
    suspend fun refreshDb(contacts: List<Contact>) {
        clearDb()
        populateDb(contacts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun populateDb(contacts: List<Contact>)

    @Insert
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM Contact")
    suspend fun clearDb()

    @Query("SELECT * FROM Contact")
    fun observeContacts(): LiveData<List<Contact>>

    @Query("SELECT MAX(id) FROM Contact")
    suspend fun getMaxId(): Int?
}