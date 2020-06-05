package com.test.app_43_testappcontacts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.test.app_43_testappcontacts.data.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val startContacts = listOf(
    Contact(1, "Alex", "Boyarchuk", "alexei.blizzard@gmail.com"),
    Contact(2, "Helen", "Shulga", "helen12345@gmail.com"),
    Contact(3, "Pasha", "Gabchak", "pasha777@mail.ru"),
    Contact(4, "Bob", "Johns", "bobjohns@gmail.com"),
    Contact(5, "Ann", "Kovtun", "annkovtunn@gmail.com")
)

@Database(entities = [Contact::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        private var INSTANCE: ContactsDatabase? = null

        fun getCurrenciesDatabase(context: Context): ContactsDatabase {
            if (INSTANCE == null) {
                synchronized(ContactsDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(context.applicationContext, ContactsDatabase::class.java, "contactsDB")
                        .addCallback(object: RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                INSTANCE?.populateDb()
                            }
                        })
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    private fun populateDb() {
        val dao = contactsDao()
        CoroutineScope(Dispatchers.IO).launch {
            dao.populateDb(startContacts)
        }
    }
}