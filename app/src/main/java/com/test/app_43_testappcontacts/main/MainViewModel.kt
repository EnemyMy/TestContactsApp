package com.test.app_43_testappcontacts.main

import android.util.Log
import androidx.lifecycle.*
import com.test.app_43_testappcontacts.Event
import com.test.app_43_testappcontacts.R
import com.test.app_43_testappcontacts.data.Contact
import com.test.app_43_testappcontacts.util.SnackbarEvent
import com.test.app_43_testappcontacts.util.ToolbarEvent
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository): ViewModel() {

    val contacts = repository.observeContacts()

    val isLoading = MutableLiveData<Boolean>()

    private val _addOrUpdateEvent = MutableLiveData<Event<Unit>>()
    val addOrUpdateEvent: LiveData<Event<Unit>> = _addOrUpdateEvent

    private val _snackbarEvent = MutableLiveData<Event<SnackbarEvent>>()
    val snackbarEvent: LiveData<Event<SnackbarEvent>> = _snackbarEvent

    private val _toolbarEvent = MutableLiveData<Event<ToolbarEvent>>()
    val toolbarEvent: LiveData<Event<ToolbarEvent>> = _toolbarEvent

    private val _backEvent = MutableLiveData<Event<Unit>>()
    val backEvent: LiveData<Event<Unit>> = _backEvent

    val isEmpty: LiveData<Boolean> = contacts.map {
        it.isEmpty()
    }

    val currentContact = MutableLiveData<Contact>()

    //two way databinding
    val contactFirstName = currentContact.map {
        it?.firstName
    } as MutableLiveData<String?>
    val contactLastName = currentContact.map {
        it?.lastName
    } as MutableLiveData<String?>
    val contactEmail = currentContact.map {
        it?.email
    } as MutableLiveData<String?>

    fun startUpdateEvent(contact: Contact) {
        currentContact.value = contact
        _addOrUpdateEvent.value = Event(Unit)
    }

    fun startAddContactEvent() {
        _addOrUpdateEvent.value = Event(Unit)
    }

    fun deleteContact(contact: Contact) {
        isLoading.value = true
        viewModelScope.launch {
            repository.deleteContact(contact)
            isLoading.value = false
        }
    }

    fun updateContact() {
        currentContact.value?.let { contact ->
            val id = contact.id
            val firstName = contactFirstName.value ?: ""
            val lastName = contactLastName.value ?: ""
            val email = contactEmail.value ?: ""
            if (validateFields(firstName, lastName, email)) {
                viewModelScope.launch {
                    repository.updateContact(Contact(id, firstName, lastName, email))
                    _backEvent.value =
                        Event(Unit)
                }
            }
            else {
                startSnackbarEvent(SnackbarEvent(R.string.snackbar_invalid_fields))
            }
        }
    }

    fun createContact() {
        val firstName = contactFirstName.value ?: ""
        val lastName = contactLastName.value ?: ""
        val email = contactEmail.value ?: ""
        if (validateFields(firstName, lastName, email)) {
            viewModelScope.launch {
                val maxId = repository.getMaxId() ?: 0
                repository.addContact(Contact(maxId + 1, firstName, lastName, email))
                _backEvent.value =
                    Event(Unit)
            }
        }
        else {
            startSnackbarEvent(SnackbarEvent(R.string.snackbar_invalid_fields))
        }
    }

    fun refreshContacts(contacts: List<Contact>) {
        isLoading.value = true
        viewModelScope.launch {
            Log.e("refreshContacts", "Inside coroutine")
            repository.refreshDb(contacts)
            Log.e("refreshContacts", "After refresh")
            isLoading.value = false
        }
    }

    fun startToolbarEvent(toolbarEvent: ToolbarEvent) {
        _toolbarEvent.value = Event(toolbarEvent)
    }

    fun startBackEvent() {
        currentContact.value = null
        _backEvent.value = Event(Unit)
    }

    private fun startSnackbarEvent(snackbarEvent: SnackbarEvent) {
        _snackbarEvent.value =
            Event(snackbarEvent)
    }

    private fun validateFields(firstName: String, lastName: String, email: String): Boolean {
        return firstName.isNotEmpty()
                && lastName.isNotEmpty()
                && email.isNotEmpty()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}