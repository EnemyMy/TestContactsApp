package com.test.app_43_testappcontacts.addorupdate

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.test.app_43_testappcontacts.EventObserver
import com.test.app_43_testappcontacts.R
import com.test.app_43_testappcontacts.databinding.FragmentAddorupdateBinding
import com.test.app_43_testappcontacts.main.MainViewModel
import com.test.app_43_testappcontacts.util.ToolbarEvent
import com.test.app_43_testappcontacts.util.setupSnackbar


class AddOrUpdateFragment(private val viewModelFactory: ViewModelProvider.NewInstanceFactory) : Fragment() {

    private lateinit var binding: FragmentAddorupdateBinding
    private val viewModel by activityViewModels<MainViewModel> {viewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddorupdateBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fragmentAddorupdatePhoto.doOnLayout {
            val card: CardView = it as CardView
            card.radius = card.width.toFloat() / 2
        }
        setupToolbar()
        setupSnackbar()
        return binding.root
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        if (viewModel.currentContact.value != null)
            viewModel.startToolbarEvent(ToolbarEvent(R.string.details, true))
        else
            viewModel.startToolbarEvent(ToolbarEvent(R.string.add_contact, true))
    }

    private fun setupSnackbar() {
        binding.root.setupSnackbar(viewLifecycleOwner, viewModel.snackbarEvent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.addorupdate_fragment_menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.addorupdate_fragment_menu_item_save -> {
                if (viewModel.currentContact.value != null)
                    viewModel.updateContact()
                else
                    viewModel.createContact()
                true
            }
            android.R.id.home -> {
                viewModel.startBackEvent()
                true
            }
            else -> false
        }
}