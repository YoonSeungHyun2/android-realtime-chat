package com.pages.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dataforms.Person
import com.kau.kotlinchatapp.R
import com.kau.kotlinchatapp.databinding.FriendListBinding
import com.recyclerViews.fragments.FriendListAdapter

class FriendListFragment: Fragment() {

    val friends: Array<Person> = arrayOf(
        Person("이인",1),
        Person("이인",2),
        Person("이인",3),
        Person("이인",4),
        Person("이인",5),
        Person("이인",6),
        Person("이인",7),
        Person("이인",8),
        Person("이인",9),
        Person("이인",10),
        Person("이인",11),
        Person("이인",12),
        Person("이인",13),
        Person("이인",14),
    )

    lateinit var binding : FriendListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.friend_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FriendListBinding.bind(view)

        binding.friendListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.friendListRecyclerView.adapter = FriendListAdapter(friends)

    }
}