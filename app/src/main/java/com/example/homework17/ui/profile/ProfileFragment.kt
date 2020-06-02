package com.example.homework17.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework17.activities.login.LoginActivity
import com.example.homework17.databinding.ProfileDataBinding
import com.example.homework17.models.User
import com.example.homework17.retrofit_api.ICallBack
import com.example.homework17.retrofit_api.RetrofitClient
import com.example.homework17.utils.Keys.PICK_IMAGE_REQUEST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    private var itemView:View? = null
    private var binding:ProfileDataBinding? = null
    private var filePath: Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mUser:User? = null
    private val user = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (itemView == null){
            binding = ProfileDataBinding.inflate(inflater,container,false)
            itemView = binding!!.root
        }
        return itemView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }
    private  fun init(){
        mUser = User()
        binding!!.info = mUser
        RetrofitClient.getUser(user.currentUser!!.uid,object :ICallBack{
            override fun onSuccess(body: Any) {
                mUser = body as User
                binding!!.info = mUser
            }
            override fun onFailure(e: String) {

            }
        })
        binding!!.root.addImageButton.setOnClickListener {
            coosImage()
        }
        binding!!.root.signOutButton.setOnClickListener {
            user.signOut()
            val intent = Intent(activity,
                LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    private fun coosImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
             filePath = data!!.data
            uploadImage(filePath!!)
        }
    }

    private fun uploadImage(filePath:Uri){
        val riversRef: StorageReference = mStorageRef!!.child("images/${user!!.uid}/profile.jpg")
        riversRef.putFile(filePath)
            .addOnSuccessListener {
                riversRef.downloadUrl.addOnSuccessListener {
                    val profileUpdate: UserProfileChangeRequest = UserProfileChangeRequest.Builder().setPhotoUri(it).build()
                    user.currentUser!!.updateProfile(profileUpdate)
                    mUser!!.profileImage = it.toString()
                    updateUserPhotoUri()
                    binding!!.invalidateAll()
                }
            }
            .addOnFailureListener {
            }
    }
    private fun updateUserPhotoUri(){
        RetrofitClient.createUser(user.currentUser!!.uid,mUser!!,object :ICallBack{
            override fun onFailure(e: String) {
                Toast.makeText(activity,e,Toast.LENGTH_LONG).show()
            }
        })
    }
}