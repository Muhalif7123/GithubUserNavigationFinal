package com.belajar.githubusernavigationfinal.data.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.belajar.githubusernavigationfinal.ui.follow.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        val bundle = Bundle()
        fragment.arguments = bundle.apply {
            putInt(SECTION_NO, position)
        }
        return fragment
    }

    companion object {
        const val SECTION_NO = "section_no"
    }
}