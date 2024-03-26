package com.belajar.githubusernavigationfinal.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.data.adapter.FollowAdapter
import com.belajar.githubusernavigationfinal.data.adapter.SectionPagerAdapter
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.databinding.FragmentFollowBinding
import com.belajar.githubusernavigationfinal.ui.DetailViewModel


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory()
        )[DetailViewModel::class.java]

        val position = arguments?.getInt(SectionPagerAdapter.SECTION_NO)

        if (position == 0) {
            viewModel.followings.observe(viewLifecycleOwner) {
                recyclerViewTab(it)
            }
        } else {
            viewModel.followers.observe(viewLifecycleOwner) {
                recyclerViewTab(it)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun recyclerViewTab(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = FollowAdapter()
        val rvContainerTab = requireView().findViewById<RecyclerView>(R.id.rv_container_tab)
        rvContainerTab.layoutManager = layoutManager
        rvContainerTab.adapter = adapter
        adapter.submitList(items)
    }

    private fun showLoading(value: Boolean) {
        if (value) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.GONE
    }
}