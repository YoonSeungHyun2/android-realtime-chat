package com.pages.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kau.kotlinchatapp.R.*
import com.kau.kotlinchatapp.databinding.ChatListBinding
import com.recyclerViews.fragments.ChatRoom
import com.recyclerViews.fragments.ChatRoomAdapter
import com.recyclerViews.fragments.Person

class ChatListFragment: Fragment() {

    private val chatRooms = arrayOf(
        ChatRoom("1", "1채팅방입니다.", Person.NAME),
        ChatRoom("2", "2채팅방입니다.", Person.NAME),
        ChatRoom("3", "3채팅방입니다.", Person.NAME),
        ChatRoom("4", "4채팅방입니다.", Person.NAME),
        ChatRoom("5", "5채팅방입니다.", Person.NAME),
        ChatRoom("6", "6채팅방입니다.", Person.NAME),
        ChatRoom("6", "6채팅방입니다.", Person.NAME),
        ChatRoom("6", "6채팅방입니다.", Person.NAME),
        ChatRoom("6", "6채팅방입니다.", Person.NAME),
        ChatRoom("6", "6채팅방입니다.", Person.NAME),
        ChatRoom("6", "6채팅방입니다.", Person.NAME),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ChatListBinding.bind(view)
        binding.chatListRecylcerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatListRecylcerView.adapter = ChatRoomAdapter(chatRooms)

    }
}