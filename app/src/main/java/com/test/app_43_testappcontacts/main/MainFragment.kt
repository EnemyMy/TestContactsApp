package com.test.app_43_testappcontacts.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.app_43_testappcontacts.R
import com.test.app_43_testappcontacts.data.local.startContacts
import com.test.app_43_testappcontacts.databinding.FragmentMainBinding
import com.test.app_43_testappcontacts.util.ToolbarEvent

class MainFragment(private val viewModelFactory: ViewModelProvider.NewInstanceFactory) : Fragment(), SwipeRefreshLayout.OnRefreshListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerAdapter: MainFragmentRecyclerAdapter
    private val viewModel by activityViewModels<MainViewModel> {viewModelFactory}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        recyclerAdapter = setupAdapter()
        setupToolbar()
        setupRecycler()
        setupSwipeRefresh()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_fragment_menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.main_fragment_menu_item_add -> {
                viewModel.startAddContactEvent()
                true
            }
            else -> false
        }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        viewModel.startToolbarEvent(ToolbarEvent(R.string.app_name, false))
    }

    private fun setupSwipeRefresh() {
        binding.fragmentMainSwipeRefresh.setOnRefreshListener(this)
    }

    private fun setupRecycler() {
        with(binding.fragmentMainRecycler) {
            adapter = recyclerAdapter
            val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
                RecyclerItemTouchHelper(this@MainFragment, 0, ItemTouchHelper.LEFT)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }
    }

    private fun setupAdapter(): MainFragmentRecyclerAdapter {
        return MainFragmentRecyclerAdapter(viewModel).apply {
            viewModel.contacts.observe(viewLifecycleOwner, Observer { list ->
                viewModel.isLoading.value = true
                this.submitList(list)
                viewModel.isLoading.value = false
            })
        }
    }

    override fun onRefresh() {
        Log.e("ViewModel: ", "$viewModel")
        viewModel.refreshContacts(startContacts)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        val contact = recyclerAdapter.currentList[position]
        viewModel.deleteContact(contact)
    }
}