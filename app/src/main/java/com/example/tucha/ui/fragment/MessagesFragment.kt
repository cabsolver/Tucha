package com.example.tucha.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tucha.TuchaApplication
import com.example.tucha.databinding.FragmentMessagesBinding
import com.example.tucha.network.TuchaApi
import com.example.tucha.repository.MessagesRepository
import com.example.tucha.ui.adapter.MessagesListAdapter
import com.example.tucha.viewmodel.MessagesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessagesFragment : Fragment() {
    private val viewModel: MessagesViewModel by viewModels {
        val app = (activity?.application) as TuchaApplication
        MessagesViewModel.MessagesViewModelFactory(
            MessagesRepository(
                app.database,
                TuchaApi.vkClient
            ),
            arguments?.getInt("dialog_id")!!
        )
    }

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private val count: Int = 20
    private var offset: Int = count
    private val refreshFrequency = 3000L

    override fun onResume() {
        super.onResume()
        refresh(viewModel.dialogId, count, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)

        val adapter = MessagesListAdapter()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    refresh(viewModel.dialogId, 5, 0)
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
            refresh(viewModel.dialogId, count, offset)
            offset += count
            binding.swipeRefresh.isRefreshing = false
        }

        binding.textInputContainer.setEndIconOnClickListener {
            sendMessage()
        }
        return binding.root
    }

    private fun refresh(id: Int, count: Int, offset: Int) {
        viewModel.refreshMessagesFromRepo(id, count, offset)
    }

    private fun sendMessage() {
        val message = binding.textInput.text.toString()
        if (message.isNotBlank()) {
            viewModel.sendTextMessage(viewModel.dialogId, message)
            refresh(viewModel.dialogId, count, 0)
            binding.textInput.setText("")
        } else {
            Toast.makeText(context, "Please enter message first!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}