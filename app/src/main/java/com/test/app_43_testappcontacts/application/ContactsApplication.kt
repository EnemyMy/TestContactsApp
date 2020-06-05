package com.test.app_43_testappcontacts.application

import android.app.Application
import androidx.fragment.app.FragmentFactory
import com.test.app_43_testappcontacts.FragmentFactoryImpl
import com.test.app_43_testappcontacts.main.MainRepositoryImpl
import com.test.app_43_testappcontacts.main.MainViewModelFactory
import com.test.app_43_testappcontacts.data.local.ContactsDatabase
import com.test.app_43_testappcontacts.data.local.LocalDataSource
import com.test.app_43_testappcontacts.addorupdate.AddOrUpdateFragment
import com.test.app_43_testappcontacts.main.MainFragment

class ContactsApplication: Application() {
    val fragmentFactory by lazy { init() }
    val viewModelFactory by lazy { initViewModel() }

    private fun init(): FragmentFactory {
        //manual DI
        val creator = mapOf(
            Pair(MainFragment::class.java, MainFragment(viewModelFactory)),
            Pair(AddOrUpdateFragment::class.java, AddOrUpdateFragment(viewModelFactory))
        )
        return FragmentFactoryImpl(creator)
    }

    private fun initViewModel(): MainViewModelFactory {
        val dao = ContactsDatabase.getCurrenciesDatabase(this.applicationContext).contactsDao()
        val dataSource = LocalDataSource(dao)
        val repository =
            MainRepositoryImpl(dataSource)
        return MainViewModelFactory(
            repository
        )
    }
}