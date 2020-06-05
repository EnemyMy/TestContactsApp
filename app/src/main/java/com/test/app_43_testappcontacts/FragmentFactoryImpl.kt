package com.test.app_43_testappcontacts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class FragmentFactoryImpl (private val creator: Map<Class<out Fragment>, Fragment>): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        return creator[fragmentClass] ?: super.instantiate(classLoader, className)
    }
}