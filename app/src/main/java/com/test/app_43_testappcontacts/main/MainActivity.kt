package com.test.app_43_testappcontacts.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.test.app_43_testappcontacts.EventObserver
import com.test.app_43_testappcontacts.R
import com.test.app_43_testappcontacts.application.ContactsApplication
import com.test.app_43_testappcontacts.databinding.ActivityMainBinding
import com.test.app_43_testappcontacts.addorupdate.AddOrUpdateFragment
import com.test.app_43_testappcontacts.util.navigateToFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { (application as ContactsApplication).viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = (application as ContactsApplication).fragmentFactory
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupBackPress()
        setupNavigation()
        if (savedInstanceState == null)
            supportFragmentManager.navigateToFragment(MainFragment::class.java,
                R.id.activity_main_fragmentContainer, false)
    }

    private fun setupBackPress() {
        viewModel.backEvent.observe(this, EventObserver {
            onBackPressed()
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.activityMainToolbar)
        viewModel.toolbarEvent.observe(this,
            EventObserver { event ->
                supportActionBar?.apply {
                    title = getString(event.titleResource)
                    setDisplayHomeAsUpEnabled(event.isBackButtonEnabled)
                }
            })
    }

    private fun setupNavigation() {
        viewModel.addOrUpdateEvent.observe(this,
            EventObserver {
                navigateToAddOrUpdate()
            })
    }

    private fun navigateToAddOrUpdate() {
        supportFragmentManager.navigateToFragment(AddOrUpdateFragment::class.java,
            R.id.activity_main_fragmentContainer, true, "detailFragment")
    }

    override fun onBackPressed() {
        viewModel.currentContact.value = null
        //hide keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        super.onBackPressed()
    }
}