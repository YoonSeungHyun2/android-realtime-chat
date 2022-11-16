package com.kau.kotlinchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kau.kotlinchatapp.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity() {

    // get reference of the firebase auth
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        auth=FirebaseAuth.getInstance()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")

        // fill otp and call the on click on button
        findViewById<Button>(R.id.sign_up_btn).setOnClickListener {
            val otp = findViewById<EditText>(R.id.et_otp).text.trim().toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.signUpBtn.setOnClickListener{
//            val otp = binding.etOtp.text.trim().toString()
//            Toast.makeText(applicationContext, otp,Toast.LENGTH_SHORT).show()
//            if(otp.isNotEmpty()){
//                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
//                    storedVerificationId.toString(),otp
//                )
//                signInWithPhoneAuthCredential(credential)
//
//            }else{
//                Toast.makeText(applicationContext,"Enter OTP", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
