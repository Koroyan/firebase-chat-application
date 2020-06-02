package com.example.homework17.activities.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.widget.Toast
import com.example.homework17.R
import com.example.homework17.activities.dashboard.DashboardActivity
import com.example.homework17.models.User
import com.example.homework17.retrofit_api.ICallBack
import com.example.homework17.retrofit_api.RetrofitClient
import com.example.homework17.utils.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var name:String
    private lateinit var  phone:String
    private lateinit var position:String
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
             phone = phoneEditText.text.toString()
             position = positionEditText.text.toString()
            name = nameEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (mail.isEmpty() || password.isEmpty() || name.isEmpty() || position.isEmpty() || phone.isEmpty()) {
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
            val profileUpdate: UserProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
            user.updateProfile(profileUpdate).addOnCompleteListener {
                }
            Handler().postDelayed({
                startActivity()
            },2000)
        }
    }

    private fun startActivity(){
        val intent = Intent(this, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    private fun createUser(user:FirebaseUser){
        val mUser = User()
        mUser.name = name
        mUser.phone = phone
        mUser.position = position
        mUser.email = user.email.toString()
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
