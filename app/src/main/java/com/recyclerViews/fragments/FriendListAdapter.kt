package com.recyclerViews.fragments


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dataforms.Person
import com.kau.kotlinchatapp.databinding.ChatListBinding
import com.kau.kotlinchatapp.databinding.FragmentFriendListCardBinding

class FriendListAdapter(val friendList:Array<Person>): RecyclerView.Adapter<FriendListAdapter.Holder>(){

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): Holder{
        val binding = FragmentFriendListCardBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder:Holder, position:Int){
        holder.bind(friendList[position])
    }

    class Holder(private val binding: FragmentFriendListCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(person: Person){
            binding.personNameTxt.text = person.name
//            binding.personImgView
        }

    }

    override fun getItemCount(): Int {
        return friendList.size
    }


}