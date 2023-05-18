package com.example.tucha.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.tucha.R
import com.example.tucha.TuchaApplication
import com.example.tucha.databinding.ActionBarBinding
import com.example.tucha.databinding.FragmentMessagesBinding
import com.example.tucha.domain.DomainMessage
import com.example.tucha.network.TuchaApi
import com.example.tucha.repository.MessagesRepository
import com.example.tucha.ui.adapter.MessagesListAdapter
import com.example.tucha.viewmodel.MessagesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessagesFragment : Fragment() {
    companion object{
        const val FAVORITE = 1
        const val FORWARD = 2
        const val EDIT = 3
        const val DELETE = 4
    }

    private val viewModel: MessagesViewModel by viewModels {
        val app = (activity?.application) as TuchaApplication
        val currentDialog = arguments?.let { MessagesFragmentArgs.fromBundle(it).dialog }
        MessagesViewModel.MessagesViewModelFactory(
            MessagesRepository(
                app.database,
                TuchaApi.vkClient,
                TuchaApi.telegramClient
            ),
            currentDialog!!
        )
    }

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private val count: Int = 20
    private var offset: Int = count
    private val refreshFrequency = 3000L
    private lateinit var currentMessage: DomainMessage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        refresh(count, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dialog_option_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)

        val activity = (activity as AppCompatActivity)

        activity.supportActionBar?.setDisplayShowCustomEnabled(true)
        val actionBarBinding = ActionBarBinding.inflate(inflater, container, false)
        activity.supportActionBar?.customView = actionBarBinding.root
        actionBarBinding.apply {
            dialogName.text = viewModel.dialog.name
            if (viewModel.dialog.photoUrl == "") {
                dialogPhoto.setImageResource(R.mipmap.ic_avatar)
            } else {
                dialogPhoto.load(viewModel.dialog.photoUrl)
            }
            when (viewModel.dialog.messengerType) {
                "telegram" -> dialogMessengerType.setImageResource(R.drawable.ic_telegram_24)
                "vk" -> dialogMessengerType.setImageResource(R.drawable.ic_vk_24)
                else -> dialogMessengerType.setImageResource(R.drawable.ic_send_24)
            }
        }

//        activity.supportActionBar?.
//        activity.supportActionBar?.subtitle = viewModel.dialog.messengerType

        val adapter = MessagesListAdapter {
            currentMessage = it
        }
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    refresh(5, 0)
                    delay(refreshFrequency)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.messages.collect {
                adapter.submitList(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh(count, offset)
            offset += count
            binding.swipeRefresh.isRefreshing = false
        }

        binding.textInputContainer.setEndIconOnClickListener {
            sendMessage()
        }
        return binding.root
    }

    private fun refresh(count: Int, offset: Int) {
        viewModel.refreshMessagesFromRepo(count, offset)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.order) {
            FORWARD -> {
                Toast.makeText(
                    context,
                    "FORWARD ${item.itemId}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            FAVORITE -> {
                Toast.makeText(
                    context,
                    "FAVORITE",
                    Toast.LENGTH_SHORT
                ).show()
            }
            EDIT -> {
                editMessage(currentMessage.id)
            }
            DELETE -> {
                deleteMessage(currentMessage.id)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun sendMessage() {
        val message = binding.textInput.text.toString()
        if (message.isNotBlank()) {
            viewModel.sendTextMessage(message)
            refresh(count, 0)
            binding.textInput.setText("")
        } else {
            Toast.makeText(context, "Please enter message first!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun favoriteMessage() {

    }

    private fun forwardMessage() {

    }

    private fun deleteMessage(messageId: Int) {
        viewModel.deleteMessage(messageId)
    }

    private fun editMessage(messageId: Int) {
        showKeyboard()
        binding.textInputContainer.setStartIconDrawable(R.drawable.cancel_24)
        binding.textInputContainer.setStartIconOnClickListener {
            disableMessageEditing()
        }
        binding.textInput.setText(currentMessage.text)
        binding.textInputContainer.setEndIconOnClickListener {
            val text = binding.textInput.text.toString()
            if (text.isNotBlank()) {
                viewModel.editTextMessage(messageId, text)
                disableMessageEditing()
            } else {
                Toast.makeText(
                    context,
                    "Please enter message first!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun disableMessageEditing() {
        binding.textInput.setText("")
        binding.textInputContainer.isStartIconVisible = false
        binding.textInputContainer.setStartIconDrawable(R.drawable.attach_file_24)
        binding.textInputContainer.setEndIconOnClickListener {
            sendMessage()
        }
    }

    private fun showKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.textInput.rootView, InputMethodManager.SHOW_IMPLICIT)
        binding.textInput.requestFocus()
    }
}