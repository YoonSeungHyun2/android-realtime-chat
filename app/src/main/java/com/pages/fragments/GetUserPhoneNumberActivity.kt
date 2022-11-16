package com.pages.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dataforms.UserModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kau.kotlinchatapp.MainActivity
import com.kau.kotlinchatapp.OtpActivity
import com.kau.kotlinchatapp.databinding.ActivityGetUserPhoneNumberBinding
import java.util.concurrent.TimeUnit

class GetUserPhoneNumberActivity : AppCompatActivity() {

    private lateinit var number: String
    private lateinit var binding: ActivityGetUserPhoneNumberBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var code: String? = null
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetUserPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.phoneSubmitBtn.setOnClickListener{
            number = "+82${binding.inputPhoneTxt.text.toString().trim()}"
            if (checkNumber()) {
                sendCode(number)
            }
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userModel =
                            UserModel(
                                "", "", "",
                                firebaseAuth.currentUser!!.phoneNumber!!,
                                firebaseAuth.uid!!
                            )

                        databaseReference.child(firebaseAuth.uid!!).setValue(userModel)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException)
                    Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_SHORT).show()
                if (e is FirebaseTooManyRequestsException)
                    Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_SHORT).show()
                else Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId= verificationId
                resendToken = token

                startActivity(Intent(applicationContext, OtpActivity::class.java).putExtra("storedVerificationId", storedVerificationId))
                finish()
            }
        }
    }





    private fun checkNumber(): Boolean {
        if (number.isEmpty()) {
            binding.inputHintTxt.text = "번호를 입력 해 주세요"
            Toast.makeText(applicationContext, "번호를 입력 해 주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun sendCode(number : String){
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}