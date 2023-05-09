package com.example.tucha.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tucha.TuchaApplication
import com.example.tucha.databinding.FragmentDialogsListBinding
import com.example.tucha.viewmodel.DialogViewModel

class DialogFragment : Fragment() {

    private val viewModel: DialogViewModel by activityViewModels {
        DialogViewModel.DialogViewModelFactory(
            activity?.application as TuchaApplication
        )
    }

    private var _binding: FragmentDialogsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogsListBinding.inflate(inflater, container, false)

        val adapter = DialogListAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.dialogs.observe(this.viewLifecycleOwner) { dialogs ->
            dialogs.let {
                adapter.submitList(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        return binding.root
    }

    private fun refresh() {
        viewModel.refreshDialogsFromRepo()
        binding.swipeRefresh.isRefreshing = false
    }
}