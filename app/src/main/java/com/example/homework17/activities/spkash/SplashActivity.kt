package com.example.homework17.activities.spkash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.homework17.R
import com.example.homework17.activities.dashboard.DashboardActivity
import com.example.homework17.activities.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser
    private var mDelayHandler: Handler? = null
    companion object {
        const val SPLASH_DELAY: Long = 2000
        const val DASHBOARD_ACTIVITY = "dashboard"
        const val LOGIN_ACTIVITY = "login"
    }
    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (user!=null) {
               startActivity(DASHBOARD_ACTIVITY)
            }else{
                startActivity(LOGIN_ACTIVITY)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init(){
        mDelayHandler = Handler()
    }

    public override fun onResume(){
        mDelayHandler!!.postDelayed(mRunnable,
            SPLASH_DELAY
        )
        super.onResume()
    }


    public override fun onStop() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onStop()
    }

    fun startActivity(activity:String){
        var intent:Intent? = null
        if(activity == DASHBOARD_ACTIVITY) {
             intent = Intent(applicationContext, DashboardActivity::class.java)
        }else{
            intent = Intent(applicationContext, LoginActivity::class.java)
        }
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

