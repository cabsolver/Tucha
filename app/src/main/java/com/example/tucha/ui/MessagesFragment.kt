package com.example.tucha.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tucha.TuchaApplication
import com.example.tucha.databinding.FragmentMessagesBinding
import com.example.tucha.viewmodel.DialogViewModel

class MessagesFragment : Fragment() {

    private var columnCount = 1
    private val viewModel: DialogViewModel by activityViewModels {
        DialogViewModel.DialogViewModelFactory(
            activity?.application as TuchaApplication
        )
    }

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        refresh()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)

        val adapter = MessagesListAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.messages.observe(this.viewLifecycleOwner) { messages ->
            messages.let {
                adapter.submitList(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        return binding.root
    }

    private fun refresh() {
        viewModel.refreshMessagesFromRepo()
        binding.swipeRefresh.isRefreshing = false
    }
}