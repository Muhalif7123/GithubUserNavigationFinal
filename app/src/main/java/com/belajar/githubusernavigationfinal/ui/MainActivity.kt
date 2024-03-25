package com.belajar.githubusernavigationfinal.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.ViewModelFactory
import com.belajar.githubusernavigationfinal.databinding.ActivityMainBinding
import com.belajar.githubusernavigationfinal.ui.setting.SettingPreference
import com.belajar.githubusernavigationfinal.ui.setting.SettingViewModel
import com.belajar.githubusernavigationfinal.ui.setting.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_favorite, R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val settingPreference = SettingPreference.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this,
            ViewModelFactory(settingPreference = settingPreference)
        )[SettingViewModel::class.java]

        viewModel.getDarkMode().observe(this) {
            AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

    companion object {
        const val TAG = "mainActivity"
    }
}