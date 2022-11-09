package com.recyclerViews.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kau.kotlinchatapp.databinding.FragmentChatRoomCardBinding


class ChatRoomAdapter(val rooms:Array<ChatRoom>) : RecyclerView.Adapter<ChatRoomAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentChatRoomCardBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(rooms[position])
    }

    class Holder(private val binding: FragmentChatRoomCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(room:ChatRoom){
            binding.txtRoomTitle.text = room.room_title
            binding.txtRoomHeadMessage.text = room.head_message
            binding.txtRoomMembers.text = room.members.toString()
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }


}