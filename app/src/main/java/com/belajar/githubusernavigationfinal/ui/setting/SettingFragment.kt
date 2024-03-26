package com.belajar.githubusernavigationfinal.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.belajar.githubusernavigationfinal.ViewModelFactory
import com.belajar.githubusernavigationfinal.data.entity.DataModel
import com.belajar.githubusernavigationfinal.databinding.FragmentSettingBinding
import com.belajar.githubusernavigationfinal.ui.DataFormActivity
import com.belajar.githubusernavigationfinal.ui.home.HomeFragment


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it?.data != null && it.resultCode == DataFormActivity.RESULT_CODE) {
            val dataModel =
                it.data?.getParcelableExtra<DataModel>(DataFormActivity.EXTRA_RESULT) as DataModel

            try {
                HomeFragment().populateView(dataModel)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingPreference =
            SettingPreference.getInstance(requireActivity().application.dataStore)

        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(settingPreference = settingPreference)
        )[SettingViewModel::class.java]

        viewModel.getDarkMode().observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
            binding.switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.saveDarkMode(isChecked)
            }
        }

        binding.cardProfile.setOnClickListener {
            val intent = Intent(requireActivity(), DataFormActivity::class.java)
            resultLauncher.launch(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}