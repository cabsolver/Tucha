package com.example.tucha.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tucha.databinding.MessageItemInBinding
import com.example.tucha.databinding.MessageItemOutBinding
import com.example.tucha.domain.DomainMessage

class MessagesListAdapter()
    : ListAdapter<DomainMessage, MessagesListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == MESSAGE_VIEW_TYPE.MESSAGE_SENT.ordinal) {
            return SentMessageViewHolder(
                MessageItemOutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ReceivedMessageViewHolder(
                MessageItemInBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentType = getItem(position).out
        if (currentType == 0) {
            return MESSAGE_VIEW_TYPE.MESSAGE_RECEIVED.ordinal
        } else {
            return MESSAGE_VIEW_TYPE.MESSAGE_SENT.ordinal
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    abstract class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        abstract fun bind(message: DomainMessage)
    }

    class SentMessageViewHolder(private var binding: MessageItemOutBinding) :
        ViewHolder(binding.root) {

        override fun bind(message: DomainMessage) {
            binding.messageContent.text = message.text
            binding.timestamp.text = message.formattedTimestamp
        }
    }

    class ReceivedMessageViewHolder(private var binding: MessageItemInBinding) :
        ViewHolder(binding.root) {

        override fun bind(message: DomainMessage) {
            binding.messageContent.text = message.text
            binding.timestamp.text = message.formattedTimestamp
        }
    }

    enum class MESSAGE_VIEW_TYPE{
        MESSAGE_RECEIVED,
        MESSAGE_SENT
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