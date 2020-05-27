package com.example.homework17.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.homework17.BaseFragment

class ViewPagerAdapter(fm:FragmentManager,private val fragments:ArrayList<BaseFragment>): FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
       return fragments[position]
    }

    override fun getCount(): Int = fragments.size
}