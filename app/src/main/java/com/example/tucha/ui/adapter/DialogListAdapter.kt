package com.example.tucha.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
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

            binding.apply {

                if (dialog.unread != null)
                    unreadCount.text = dialog.unread.toString()

                dialogPhoto.load(dialog.photoUrl) {
                    transformations(CircleCropTransformation())
                }

                dialogName.text = dialog.name
                date.text = dialog.formattedDate
                lastMessage.text = dialog.lastMessage

                when (dialog.messengerType) {
                    "telegram" -> dialogMessengerType.setImageResource(R.drawable.ic_telegram_24)
                    "vk" -> dialogMessengerType.setImageResource(R.drawable.ic_vk_24)
                    else -> dialogMessengerType.setImageResource(R.drawable.ic_send_24)
                }


                clickableOverlay.setOnClickListener {
                    val bundle = bundleOf(
                        "dialog_id" to dialog.id,
                        "dialog_name" to dialog.name
                    )
                    root.findNavController()
                        .navigate(R.id.action_dialogFragment_to_messagesFragment, bundle)
                }
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