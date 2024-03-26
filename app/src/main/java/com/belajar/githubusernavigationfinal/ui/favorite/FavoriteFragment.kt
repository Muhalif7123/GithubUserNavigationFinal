package com.belajar.githubusernavigationfinal.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.githubusernavigationfinal.ViewModelFactory
import com.belajar.githubusernavigationfinal.data.adapter.HomeAdapter
import com.belajar.githubusernavigationfinal.databinding.FragmentFavoriteBinding
import com.belajar.githubusernavigationfinal.ui.home.HomeViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels { factory }

        val homeAdapter = HomeAdapter {
            if (it.favorite) {
                viewModel.deleteUser(it)
            } else {
                viewModel.saveUser(it)
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvContainerFavorite.adapter = homeAdapter
        binding.rvContainerFavorite.layoutManager = layoutManager

        viewModel.getFavorite().observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            homeAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}