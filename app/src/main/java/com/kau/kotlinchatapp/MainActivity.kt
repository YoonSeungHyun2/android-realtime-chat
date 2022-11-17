package com.kau.kotlinchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kau.kotlinchatapp.databinding.ActivityMainBinding
import com.pages.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    private fun replaceFragment(fragment: Fragment):Unit{
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(applicationContext,DashBoardActivity()::class.java)
            startActivity(intent)
            finish()
        } else {
            replaceFragment(GetUserPhoneNumberFragment())

        }
//        replaceFragment(GetUserPhoneNumberFragment())
    }

}

