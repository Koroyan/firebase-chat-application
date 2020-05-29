package com.example.homework17.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.homework17.BaseFragment
import com.example.homework17.R
import com.example.homework17.adapters.ViewPagerAdapter
import com.example.homework17.ui.message.MessageFragment
import com.example.homework17.ui.home.HomeFragment
import com.example.homework17.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : AppCompatActivity() {

    private val fragments = arrayListOf<BaseFragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        viewPagerListener()
        navViewListener()
    }

    private fun init() {
        fragments.add(HomeFragment())
        fragments.add(MessageFragment())
        fragments.add(NotificationsFragment())
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragments)
    }

    private fun viewPagerListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                navView.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun navViewListener() {
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_dashboard -> viewPager.currentItem = 1
                R.id.navigation_notifications -> viewPager.currentItem = 2
            }
            true
        }
    }
}
