package com.example.homework17.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import com.example.homework17.R
import com.example.homework17.data.SharedPreferenceData
import com.example.homework17.models.User
import com.example.homework17.retrofit_api.ICallBack
import com.example.homework17.retrofit_api.RetrofitClient
import com.example.homework17.utils.Keys
import com.example.homework17.utils.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()
        registerButton.setOnClickListener {
            val mail = mailEditText.text.toString()
            val password = passwordEditText.text.toString()
            name = nameEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (mail.isEmpty() || password.isEmpty() || name.isEmpty()) {
                errorTextView.text = getString(R.string.please_fill_all_fields)
                return@setOnClickListener
            }
            if (!Patterns.validMail(mail)) {
                errorTextView.text = getString(R.string.incorrect_email_address)
                return@setOnClickListener
            } else {
                errorTextView.text = ""
            }
            if (password != confirmPassword) {
                errorTextView.text = getString(R.string.password_confirmation_does_not_match)
                return@setOnClickListener
            } else {
                errorTextView.text = ""
            }
            signUp(mail,password)
        }
    }

    private fun signUp(email: String, password: String) {
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                   d("FragmentActivity.TAG", "createUserWithEmail:success")
                    val user: FirebaseUser? = mAuth?.currentUser
                    updateUI(user)
                } else {
                   d(
                        "FragmentActivity.TAG",
                        "createUserWithEmail:failure",
                        task.exception
                    )

                    updateUI(null)
                }


            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user!=null) {
            createUser(user)
            val intent = Intent(this, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    private fun createUser(user:FirebaseUser){
        val mUser:User = User()
        mUser.name = name
        mUser.email = user.email.toString()
        mUser.id = user.uid
        SharedPreferenceData.sharedPreferenceData.pushString(Keys.NAME,name)
        SharedPreferenceData.sharedPreferenceData.pushString(Keys.ID,user.uid)
        RetrofitClient.createUser(user.uid,mUser,object : ICallBack {
            override fun onFailure(e: String) {
                Toast.makeText(this@SignUpActivity,e,Toast.LENGTH_SHORT).show()
                return
            }
        })
    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }
}
