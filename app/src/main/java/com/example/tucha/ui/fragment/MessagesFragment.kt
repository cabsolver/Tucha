package com.example.tucha.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tucha.TuchaApplication
import com.example.tucha.databinding.FragmentMessagesBinding
import com.example.tucha.network.TuchaApi
import com.example.tucha.repository.MessagesRepository
import com.example.tucha.ui.adapter.MessagesListAdapter
import com.example.tucha.viewmodel.MessagesViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        refresh(viewModel.dialogId)
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
            viewModel.messages.collect {
                adapter.submitList(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh(viewModel.dialogId)
        }
        return binding.root
    }

    private fun refresh(id: Int) {
        viewModel.refreshMessagesFromRepo(id)
        binding.swipeRefresh.isRefreshing = false
    }
}