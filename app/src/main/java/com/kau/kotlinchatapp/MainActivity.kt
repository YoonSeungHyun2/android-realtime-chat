package com.kau.kotlinchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kau.kotlinchatapp.databinding.ActivityMainBinding
import com.pages.fragments.ChatListFragment
import com.pages.fragments.FriendListFragment
import com.pages.fragments.NotificationFragment
import com.pages.fragments.SettingFragment

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

