package com.example.rass_education.Home.tugas_p10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LandPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LandListFragment.newInstance(isGrid = false) // Tab List
            else -> LandListFragment.newInstance(isGrid = true) // Tab Grid
        }
    }
}


