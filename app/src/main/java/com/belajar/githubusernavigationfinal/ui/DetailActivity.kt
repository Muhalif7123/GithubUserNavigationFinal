package com.belajar.githubusernavigationfinal.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.data.adapter.SectionPagerAdapter
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

        val apiId = intent.getStringExtra("id")
        viewModel.getDataComplete(apiId)

        viewModel.getFollowings(apiId)
        viewModel.getFollowers(apiId)

        viewModel.dataDetail.observe(this) {
            setDataComplete(it)
        }
        viewModel.loading.observe(this) {
            showLoading(it)
        }

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
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

        binding.fabShare.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            val shareText = "Visit this github profile: "
            share.putExtra(Intent.EXTRA_SUBJECT, "Github Account")
            share.putExtra(Intent.EXTRA_TEXT, "$shareText ${dataDetail.htmlUrl}")
            share.setType("text/plain")
            startActivity(Intent.createChooser(share, "Share to"))
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