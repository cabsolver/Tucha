package com.example.tucha.ui.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tucha.databinding.MessageItemInBinding
import com.example.tucha.databinding.MessageItemOutBinding
import com.example.tucha.domain.DomainMessage
import com.example.tucha.ui.fragment.MessagesFragment
import java.util.Locale

class MessagesListAdapter(private val onItemClicked: (DomainMessage) -> Unit)
    : ListAdapter<DomainMessage, MessagesListAdapter.ViewHolder>(DiffCallback) {
    private var unfilteredList: List<DomainMessage> = listOf()
    private val MESSAGE_RECEIVED = 0
    private val MESSAGE_SENT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == MESSAGE_SENT) {
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
            return MESSAGE_RECEIVED
        } else {
            return MESSAGE_SENT
        }

    }
    fun modifyList(list : List<DomainMessage>) {
        unfilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<DomainMessage>()

        // perform the data filtering
        if(!query.isNullOrEmpty()) {
            list.addAll(unfilteredList.filter {
                it.text.lowercase(Locale.getDefault()).contains(query.toString()
                    .lowercase(Locale.getDefault()))})
        } else {
            list.addAll(unfilteredList)
        }

        submitList(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
            it.showContextMenu()
        }
        holder.itemView.setOnLongClickListener {
            onItemClicked(current)
            it.showContextMenu()
        }
        holder.bind(current)
    }

    abstract class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        abstract fun bind(message: DomainMessage)
    }

    class SentMessageViewHolder(private var binding: MessageItemOutBinding) :
        ViewHolder(binding.root) {

        override fun bind(message: DomainMessage) {
            binding.messageContent.text = message.text
            binding.timestamp.text = message.formattedTimestamp
            binding.date.text = message.formattedDate
            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (menu == null) return
            menu.add(Menu.NONE, Menu.NONE, MessagesFragment.FAVORITE, "Favorite")
            menu.add(Menu.NONE, Menu.NONE, MessagesFragment.FORWARD, "Forward")
            menu.add(Menu.NONE, Menu.NONE, MessagesFragment.EDIT, "Edit")
            menu.add(Menu.NONE, Menu.NONE, MessagesFragment.DELETE, "Delete")
        }
    }

    class ReceivedMessageViewHolder(private var binding: MessageItemInBinding) :
        ViewHolder(binding.root) {

        override fun bind(message: DomainMessage) {
            binding.messageContent.text = message.text
            binding.timestamp.text = message.formattedTimestamp
            binding.date.text = message.formattedDate
            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (menu == null) return
            menu.apply {
                add(Menu.NONE, Menu.NONE, MessagesFragment.FAVORITE, "Favorite")
                add(Menu.NONE, Menu.NONE, MessagesFragment.FORWARD, "Forward")
                add(Menu.NONE, Menu.NONE, MessagesFragment.DELETE, "Delete")
            }
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