package com.kau.kotlinchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kau.kotlinchatapp.databinding.ActivityMainBinding
import com.pages.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun replaceFragment(fragment: Fragment):Unit{
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private val chatListFragment: ChatListFragment = ChatListFragment()
    private val friendListFragment: FriendListFragment = FriendListFragment()
    private val notificationFragment: NotificationFragment = NotificationFragment()
    private val settingFragment: SettingFragment = SettingFragment()
    var openLogin:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(openLogin == 1){
            val intent = Intent(applicationContext, GetUserPhoneNumberActivity::class.java)
            startActivity(intent)
        }


        replaceFragment(ChatListFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chat_list -> replaceFragment(ChatListFragment())
                R.id.notification -> replaceFragment(NotificationFragment())
                R.id.friend_list -> replaceFragment(FriendListFragment())
                R.id.setting -> replaceFragment(SettingFragment())
                else -> {}
            }
            true
        }
    }

}

