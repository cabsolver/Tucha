package com.example.tucha.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.tucha.R
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
            if (dialog.unread != null)
                binding.unreadCount.text = dialog.unread.toString()

            binding.dialogPhoto.load(dialog.photoUrl) {
                transformations(CircleCropTransformation())
            }

            binding.dialogName.text = dialog.name
            binding.date.text = dialog.formattedDate
            binding.lastMessage.text = dialog.lastMessage

            binding.clickableOverlay.setOnClickListener {
                binding.root.findNavController()
                    .navigate(R.id.action_dialogFragment_to_messagesFragment)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DomainDialog>() {
            override fun areItemsTheSame(oldItem: DomainDialog, newItem: DomainDialog): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DomainDialog, newItem: DomainDialog): Boolean {
                return oldItem.lastMessage == newItem.lastMessage
            }
        }
    }
}