package com.kau.kotlinchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kau.kotlinchatapp.databinding.ActivityDashBoardBinding
import com.pages.fragments.*

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashBoardBinding

    private fun replaceFragment(fragment: Fragment):Unit{
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainContainer, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chat_list -> replaceFragment(ChatListFragment())
                R.id.notification -> replaceFragment(NotificationFragment())
                R.id.friend_list -> replaceFragment(FriendListFragment())
                R.id.setting -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

    }
}