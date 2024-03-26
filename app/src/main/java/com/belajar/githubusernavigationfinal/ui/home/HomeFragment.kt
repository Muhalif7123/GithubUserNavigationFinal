package com.belajar.githubusernavigationfinal.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.ViewModelFactory
import com.belajar.githubusernavigationfinal.data.DataPreference
import com.belajar.githubusernavigationfinal.data.Result
import com.belajar.githubusernavigationfinal.data.adapter.HomeAdapter
import com.belajar.githubusernavigationfinal.data.entity.DataModel
import com.belajar.githubusernavigationfinal.databinding.FragmentHomeBinding
import com.belajar.githubusernavigationfinal.ui.DataFormActivity
import com.belajar.githubusernavigationfinal.ui.setting.SettingPreference
import com.belajar.githubusernavigationfinal.ui.setting.SettingViewModel
import com.belajar.githubusernavigationfinal.ui.setting.dataStore


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var dataModel: DataModel
    private lateinit var mDataPreference: DataPreference

    private var showFavoritesOnly = false

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it?.data != null && it.resultCode == DataFormActivity.RESULT_CODE) {
            val dataModel =
                it.data?.getParcelableExtra<DataModel>(DataFormActivity.EXTRA_RESULT) as DataModel
            populateView(dataModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingPreference =
            SettingPreference.getInstance(requireActivity().application.dataStore)
        val darkModeViewModel = ViewModelProvider(
            this, ViewModelFactory(settingPreference = settingPreference)
        )[SettingViewModel::class.java]

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels {
            factory
        }


        val homeAdapter = HomeAdapter {
            if (it.favorite) {
                viewModel.deleteUser(it)
            } else {
                viewModel.saveUser(it)
            }
        }

        binding.rvContainer.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContainer.adapter = homeAdapter


        binding.cardHello.btnChange.setOnClickListener {
            val intent = Intent(requireActivity(), DataFormActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.btnFavorite.cardFavorite.setOnClickListener {
            showFavoritesOnly = !showFavoritesOnly

            if (showFavoritesOnly) {
                viewModel.getFavorite().observe(viewLifecycleOwner) {
                    homeAdapter.submitList(it)
                }
            } else {
                viewModel.getData().observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Success -> {
                            homeAdapter.submitList(result.data)
                            binding.progressBar.visibility = View.GONE
                        }

                        is Result.Failure -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireActivity(),
                                "Something went wrong: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewModel.getData().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val userData = it.data
                        homeAdapter.submitList(userData)
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

        binding.btnDarkMode.cardDark.setOnClickListener {
            darkModeViewModel.getDarkMode().observe(viewLifecycleOwner) {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            val default =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            darkModeViewModel.saveDarkMode(default != Configuration.UI_MODE_NIGHT_YES)
        }

        binding.cardHello.ivWaving.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.waving)

        mDataPreference = DataPreference(requireActivity())
        getData()
    }


    fun populateView(dataModel: DataModel) {
        binding.cardHello.tvName.text =
            if (dataModel.name.toString().isEmpty()) "Guest" else dataModel.name

        binding.btnGithub.cardGithub.setOnClickListener {
            if (dataModel.githubLink.toString().isEmpty()) {
                Toast.makeText(
                    requireActivity(), "Configure Your Profile First", Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://github.com/${dataModel.githubLink}")
                )
                startActivity(intent)
            }
        }
        binding.cardHello.tvUsername.text = if (dataModel.githubLink.toString()
                .isEmpty()
        ) "Github Username: -" else "Github Username: ${dataModel.githubLink}"
    }

    private fun getData() {
        dataModel = mDataPreference.getData()
        populateView(dataModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


