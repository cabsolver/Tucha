package com.example.tucha.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tucha.databinding.MessageItemBinding
import com.example.tucha.domain.DomainMessage

class MessagesListAdapter : ListAdapter<DomainMessage, MessagesListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MessageItemBinding.inflate(
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

    class ViewHolder(private var binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: DomainMessage) {
            binding.messageContent.text = message.text
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DomainMessage>() {
            override fun areItemsTheSame(oldItem: DomainMessage, newItem: DomainMessage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DomainMessage, newItem: DomainMessage): Boolean {
                return oldItem.text == newItem.text
            }
        }
    }
}