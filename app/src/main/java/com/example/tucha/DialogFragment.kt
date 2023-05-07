package com.example.tucha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tucha.databinding.FragmentDialogsListBinding
import com.example.tucha.viewmodel.DialogViewModel

class DialogFragment : Fragment() {

    private var columnCount = 1
    private val viewModel: DialogViewModel by activityViewModels {
        DialogViewModel.DialogViewModelFactory(
            activity?.application as TuchaApplication
        )
    }

    private var _binding: FragmentDialogsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DialogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DialogListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this.context, columnCount)

        viewModel.dialogs.observe(this.viewLifecycleOwner) { dialogs ->
            dialogs.let {
                adapter.submitList(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        viewModel.refreshDataFromRepository()
        binding.swipeRefresh.isRefreshing = false
    }
}