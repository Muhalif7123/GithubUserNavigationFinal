package com.belajar.githubusernavigationfinal.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.ViewModelFactory
import com.belajar.githubusernavigationfinal.data.Result
import com.belajar.githubusernavigationfinal.data.adapter.SectionPagerAdapter
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.response.DataDetail
import com.belajar.githubusernavigationfinal.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: DetailViewModel by viewModels()

//        val recyclerView: RecyclerView = findViewById(R.id.rv_container_tab)
//        val adapter = UserAdapter()
//        val layout = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = layout


        val apiId = intent.getStringExtra("id")
        val apiLogin = intent.getStringExtra("login")
        viewModel.getDataComplete(apiId)

//        viewModel.getDataComplete(apiId).observe(this) {
//            setDataComplete(it)
//        }
        viewModel.getFollowings(apiId)
        viewModel.getFollowers(apiId)
        viewModel.getDataComplete(apiId)
//        viewModel.dataDetail.observe(this) {
//            setDataComplete(it)
//        }



        viewModel.dataDetail.observe(this) {
            setDataComplete(it)
        }
        viewModel.loading.observe(this) {
            showLoading(it)
        }

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }



        val sectionPageAdapter = SectionPagerAdapter(this)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        viewPager.adapter = sectionPageAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(SECTION_TITLE[position])
            Log.d("TabLayoutDebug", "Setting tab text: ${tab.text} at position: $position")
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    private fun setDataComplete(dataDetail: DataDetail) {
//        when(dataDetail) {
//            is Result.Success -> {
//                showLoading(false)
//                val userEntities = dataDetail.data
//               userEntities.map {
//                    val userEntity = userEntities[0]
//                   binding.tvNameDetailed.text = userEntity.name
//                   binding.tvUsernameDetailed.text = userEntity.login
//                   Glide.with(this@DetailActivity)
//                       .load(userEntity.avatarUrl)
//                       .into(binding.ivDetailedPicture)
//                   binding.tvFollowers.text =
//                       this@DetailActivity.resources.getString(R.string.followers, userEntity.followers)
//                   binding.tvFollowings.text =
//                       this@DetailActivity.resources.getString(R.string.followings, userEntity.following)
//
//                   binding.tvCompany.text = userEntity.company
//                   binding.toolbar.title = userEntity.login
//
//                   val menuOpen = findViewById<View>(R.id.menu_open)
//                   menuOpen.setOnClickListener {
//                       val open = Intent(Intent.ACTION_VIEW, Uri.parse(userEntity.htmlUrl))
//                       startActivity(open)
//                   }
//
//
//               }
//            }
//
//            is Result.Failure -> {
//                showLoading(false)
//                Toast.makeText(
//                    this,
//                    "Something went wrong: ${dataDetail.error}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            is Result.Loading -> showLoading(true)
//        }
//            val userEntity = dataDetail.data.firstOrNull() // Get the first user entity from the list        }


        binding.tvNameDetailed.text = dataDetail.name
        binding.tvUsernameDetailed.text = dataDetail.login
        Glide.with(this@DetailActivity)
            .load(dataDetail.avatarUrl)
            .into(binding.ivDetailedPicture)

        binding.tvFollowers.text =
            this@DetailActivity.resources.getString(R.string.followers, dataDetail.followers)
        binding.tvFollowings.text =
            this@DetailActivity.resources.getString(R.string.followings, dataDetail.following)

        binding.tvCompany.text = dataDetail.company
        binding.toolbar.title = dataDetail.login

        val menuOpen = findViewById<View>(R.id.menu_open)
        menuOpen.setOnClickListener {
            val open = Intent(Intent.ACTION_VIEW, Uri.parse(dataDetail.htmlUrl))
            startActivity(open)
        }
    }

    private fun showLoading(value: Boolean) {
        if (value) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.INVISIBLE
    }
    companion object {
        @StringRes
        private val SECTION_TITLE = intArrayOf(R.string.following, R.string.follower)
    }
}