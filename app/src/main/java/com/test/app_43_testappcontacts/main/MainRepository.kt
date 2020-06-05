package com.test.app_43_testappcontacts.main

import androidx.lifecycle.LiveData
import com.test.app_43_testappcontacts.data.Contact

interface MainRepository {
    suspend fun refreshDb(contacts: List<Contact>)
    suspend fun addContact(contact: Contact)
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    fun observeContacts(): LiveData<List<Contact>>
    suspend fun getMaxId(): Int?
}