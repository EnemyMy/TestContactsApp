package com.test.app_43_testappcontacts.main

import androidx.lifecycle.LiveData
import com.test.app_43_testappcontacts.data.Contact
import com.test.app_43_testappcontacts.data.local.LocalDataSource
import com.test.app_43_testappcontacts.main.MainRepository

class MainRepositoryImpl (private val dataSource: LocalDataSource): MainRepository {
    override suspend fun refreshDb(contacts: List<Contact>) = dataSource.refreshDb(contacts)

    override suspend fun addContact(contact: Contact) = dataSource.addContact(contact)

    override suspend fun updateContact(contact: Contact) = dataSource.updateContact(contact)

    override suspend fun deleteContact(contact: Contact) = dataSource.deleteContact(contact)

    override fun observeContacts(): LiveData<List<Contact>> = dataSource.observeContacts()

    override suspend fun getMaxId(): Int? = dataSource.getMaxId()
}