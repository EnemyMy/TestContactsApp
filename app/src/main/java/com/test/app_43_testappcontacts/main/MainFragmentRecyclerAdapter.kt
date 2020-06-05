package com.test.app_43_testappcontacts.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.app_43_testappcontacts.data.Contact
import com.test.app_43_testappcontacts.databinding.MainRecyclerItemBinding

class MainFragmentRecyclerAdapter (private val viewModel: MainViewModel): ListAdapter<Contact, MainFragmentRecyclerAdapter.RecyclerHolder>(IdeasDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val binding = MainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.binding.contact = currentList[position]
    }

    inner class RecyclerHolder(val binding: MainRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                viewModel.startUpdateEvent(currentList[layoutPosition])
            }
        }
    }

}

class IdeasDiffCallback: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem == newItem
}