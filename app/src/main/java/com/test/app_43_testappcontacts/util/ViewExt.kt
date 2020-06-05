package com.test.app_43_testappcontacts.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.test.app_43_testappcontacts.Event
import com.test.app_43_testappcontacts.EventObserver

fun View.setupSnackbar(owner: LifecycleOwner, event: LiveData<Event<SnackbarEvent>>) {
    event.observe(owner, EventObserver {
        showSnackbar(it)
    })
}

fun View.showSnackbar(event: SnackbarEvent) {
    Snackbar.make(this, context.getString(event.messageResource), event.length).also {
        if (event.actionTextResource != null && event.action != null) {
            it.setAction(context.getString(event.actionTextResource), event.action)
        }
    }.show()
}

