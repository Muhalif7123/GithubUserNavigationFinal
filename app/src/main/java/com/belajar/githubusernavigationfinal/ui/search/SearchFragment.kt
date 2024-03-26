package com.belajar.githubusernavigationfinal.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.githubusernavigationfinal.ViewModelFactory
import com.belajar.githubusernavigationfinal.data.Result
import com.belajar.githubusernavigationfinal.data.adapter.UserAdapter
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.databinding.FragmentSearchBinding
import com.belajar.githubusernavigationfinal.ui.home.HomeViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var allItems: List<UserEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: SearchViewModel by viewModels {
            factory
        }
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        val adapter = UserAdapter {
            if (it.favorite) {
                homeViewModel.deleteUser(it)
            } else {
                homeViewModel.saveUser(it)
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvContainerSearch.adapter = adapter
        binding.rvContainerSearch.layoutManager = layoutManager


        with(binding) {
            binding.progressBar.visibility = View.GONE
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()

                    viewModel.getDataSearch(searchBar.text.toString()).observe(viewLifecycleOwner) {
                        if (it != null) {
                            when (it) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val userData = it.data
                                    adapter.submitList(userData)

                                }

                                is Result.Failure -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        requireActivity(),
                                        "Something went wrong: ${it.error}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                    true
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}