package com.pages.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dataforms.UserModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kau.kotlinchatapp.R
import com.kau.kotlinchatapp.databinding.FragmentGetUserPhoneNumberBinding
import java.util.concurrent.TimeUnit

class GetUserPhoneNumberFragment: Fragment() {

    private lateinit var number: String
    private lateinit var binding: FragmentGetUserPhoneNumberBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var code: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private fun replaceFragment(fragment: Fragment):Unit{
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_layout, fragment)
        fragmentTransaction?.commitAllowingStateLoss()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding = FragmentGetUserPhoneNumberBinding.inflate(inflater, container, false)

        binding.phoneSubmitBtn.setOnClickListener{
            number = "+82${binding.inputPhoneTxt.text.toString().trim()}"
            if (checkNumber()) {
                sendCode(number)
            }
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(context, "로그인 성공 On 유저번호입력 프래그", Toast.LENGTH_LONG).show()
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userModel =
                            UserModel(
                                "", "", "",
                                firebaseAuth.currentUser!!.phoneNumber!!,
                                firebaseAuth.uid!!
                            )
                        databaseReference.child("${firebaseAuth.uid}").setValue(userModel);
                        replaceFragment(GetUserDataFragment())
                    }
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(context, "" + e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId= verificationId
                resendToken = token

                replaceFragment(GetOtpFragment(storedVerificationId,resendToken))
            }
        }

        return binding.root
    }


    private fun checkNumber(): Boolean {
        if (number.isEmpty()) {
            Toast.makeText(context, "번호를 입력 해 주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun sendCode(number : String){
        val options : PhoneAuthOptions
        if(activity != null){
            options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity())                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }


}