package com.example.tucha

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tucha.databinding.DialogItemBinding
import com.example.tucha.domain.DomainDialog

class DialogListAdapter : ListAdapter<DomainDialog, DialogListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ViewHolder(private var binding: DialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dialog: DomainDialog) {
            binding.dialogName.text = dialog.id.toString()
            binding.lastMessage.text = dialog.lastMessage
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DomainDialog>() {
            override fun areItemsTheSame(oldItem: DomainDialog, newItem: DomainDialog): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: DomainDialog, newItem: DomainDialog): Boolean {
                return oldItem.lastMessage == newItem.lastMessage
            }
        }
    }
}