package com.example.tucha.ui.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        fun bind(dialog: DomainDialog) {

            binding.apply {

                if (dialog.unread != null)
                    unreadCount.text = dialog.unread.toString()

                if (dialog.photoUrl == "") {
                    dialogPhoto.setImageResource(R.mipmap.ic_avatar)
                } else {
                    dialogPhoto.load(dialog.photoUrl)
                }

                dialogName.text = dialog.name
                date.text = dialog.formattedDate
                lastMessage.text = dialog.lastMessage
                clickableOverlay.setOnCreateContextMenuListener(this@ViewHolder)

                when (dialog.messengerType) {
                    "telegram" -> dialogMessengerType.setImageResource(R.drawable.ic_telegram_24)
                    "vk" -> dialogMessengerType.setImageResource(R.drawable.ic_vk_24)
                    else -> dialogMessengerType.setImageResource(R.drawable.ic_send_24)
                }

                clickableOverlay.setOnClickListener {
                    val bundle = bundleOf(
                        "dialog" to dialog
                    )
                    root.findNavController()
                        .navigate(R.id.action_dialogFragment_to_messagesFragment, bundle)
                }
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (menu != null) {
                menu.add(this.bindingAdapterPosition, 1, 0, "Delete")
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