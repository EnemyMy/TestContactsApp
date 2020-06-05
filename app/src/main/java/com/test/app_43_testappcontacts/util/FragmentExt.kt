package com.test.app_43_testappcontacts.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.app_43_testappcontacts.R

fun FragmentManager.navigateToFragment(
    fragmentClass: Class<out Fragment>,
    container: Int,
    isAnimated: Boolean,
    backStackTag: String? = ""
) {
    beginTransaction()
        .also {
            if (isAnimated)
                it.addSlideAnimation()
        }
        .replace(container, fragmentClass, null)
        .also {
            if (backStackTag != "")
                it.addToBackStack(backStackTag)
        }
        .commit()
}

fun FragmentTransaction.addSlideAnimation(): FragmentTransaction {
    this.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
    return this
}