package com.example.homework17.activities.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.homework17.R
import com.example.homework17.activities.message.MessageActivity
import com.example.homework17.activities.dashboard.adapter.ViewPagerAdapter
import com.example.homework17.ui.users.UsersFragment
import com.example.homework17.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : AppCompatActivity(),View.OnClickListener {

    private var dashboardIntent:Intent? = null
    private val fragments = arrayListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        viewPagerListener()
        navViewListener()
    }

    private fun init() {
       dashboardIntent = Intent(this,
           MessageActivity::class.java)
        messageButton.setOnClickListener(this)
        fragments.add(UsersFragment())
        fragments.add(ProfileFragment())
        viewPager.adapter =
            ViewPagerAdapter(
                supportFragmentManager,
                fragments
            )
        viewPager.scrollBarSize = 3
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
                R.id.navigation_users -> viewPager.currentItem = 0
                R.id.navigation_profile -> viewPager.currentItem = 1
            }
            true
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.messageButton -> {
                dashboardIntent!!.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(dashboardIntent)
            }
        }
    }
}
