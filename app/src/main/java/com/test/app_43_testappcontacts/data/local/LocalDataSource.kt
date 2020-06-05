package com.test.app_43_testappcontacts.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.test.app_43_testappcontacts.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource (private val contactsDao: ContactsDao) {
    suspend fun refreshDb(contacts: List<Contact>) = withContext(Dispatchers.IO) {
        contactsDao.refreshDb(contacts)
    }

    suspend fun addContact(contact: Contact) = withContext(Dispatchers.IO) {
        contactsDao.addContact(contact)
    }

    suspend fun updateContact(contact: Contact) = withContext(Dispatchers.IO) {
        contactsDao.updateContact(contact)
    }

    suspend fun deleteContact(contact: Contact) = withContext(Dispatchers.IO) {
        contactsDao.deleteContact(contact)
    }

    fun observeContacts(): LiveData<List<Contact>> = contactsDao.observeContacts()

    suspend fun getMaxId(): Int? = withContext(Dispatchers.IO) {
        return@withContext contactsDao.getMaxId()
    }
}