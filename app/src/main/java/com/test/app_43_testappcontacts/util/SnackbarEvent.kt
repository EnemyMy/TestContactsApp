package com.test.app_43_testappcontacts.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackbarEvent(val messageResource: Int, val length: Int = Snackbar.LENGTH_SHORT, val actionTextResource: Int? = null, val action: ((View) -> Unit)? = null)