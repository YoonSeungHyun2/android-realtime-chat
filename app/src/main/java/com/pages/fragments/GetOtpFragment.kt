package com.pages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dataforms.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kau.kotlinchatapp.R
import com.kau.kotlinchatapp.databinding.FragmentGetOtpBinding


class GetOtpFragment(private var verificationId: String, private var token: PhoneAuthProvider.ForceResendingToken) : Fragment() {

    private lateinit var binding : FragmentGetOtpBinding
    private lateinit var sms: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private fun replaceFragment(fragment: Fragment):Unit{
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_layout, fragment)
        fragmentTransaction?.commitAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGetOtpBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")


        binding.signUpBtn.setOnClickListener{
            if(checkSMS()){
                val credential = PhoneAuthProvider.getCredential(verificationId,sms)
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                        val userModel = UserModel(
                            "", "", "",
                            firebaseAuth.currentUser!!.phoneNumber!!,
                            firebaseAuth.uid!!)
                        databaseReference.child(firebaseAuth.uid!!).setValue(userModel)
                        replaceFragment(GetUserDataFragment())
                    }
                }
            }
        }


        return binding.root
    }




    private fun checkSMS():Boolean{
        sms = binding.etOtp.text.toString().trim()
        if(sms.isEmpty()){
            Toast.makeText(context,"Enter OTP", Toast.LENGTH_LONG).show()
            return false
        }else if (sms.length <6){
            Toast.makeText(context, "OTP는 6글자 입니다", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}