package com.example.tucha.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tucha.R
import com.example.tucha.TuchaApplication
import com.example.tucha.databinding.FragmentDialogsListBinding
import com.example.tucha.network.TuchaApi
import com.example.tucha.repository.DialogsRepository
import com.example.tucha.ui.adapter.DialogListAdapter
import com.example.tucha.viewmodel.DialogViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DialogFragment : Fragment() {

    private val viewModel: DialogViewModel by viewModels {
        val app = (activity?.application) as TuchaApplication
        DialogViewModel.DialogViewModelFactory(
            DialogsRepository(
                app.database,
                TuchaApi.vkClient,
                TuchaApi.telegramClient
            )
        )
    }

    private var _binding: FragmentDialogsListBinding? = null
    private val binding get() = _binding!!

    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dialog_list_option_menu, menu)
        val menuItem = menu.findItem(R.id.search_dialog)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnCloseListener {
            isSearching = false
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    while (!isSearching) {

                        refreshDialogs()
                        delay(viewModel.refreshFrequency)
                    }
                }
            }
            false
        }
        searchView.setOnQueryTextListener (object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                isSearching = true
                (binding.recyclerView.adapter as DialogListAdapter).filter(newText)
                return false
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("DialogTest", "onCreateView called!")
        _binding = FragmentDialogsListBinding.inflate(inflater, container, false)

        val activity = (activity as AppCompatActivity)

        activity.supportActionBar?.setDisplayShowCustomEnabled(false)

        val adapter = DialogListAdapter()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (!isSearching) {

                    refreshDialogs()
                    delay(viewModel.refreshFrequency)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.dialogs.collect {
                adapter.modifyList(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refreshDialogs()
            binding.swipeRefresh.isRefreshing = false
        }
        return binding.root
    }



    private fun refreshDialogs() {
        viewModel.refreshDialogsFromRepo()
    }
}