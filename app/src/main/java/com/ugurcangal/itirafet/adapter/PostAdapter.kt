package com.ugurcangal.itirafet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugurcangal.itirafet.databinding.PostDesignBinding
import com.ugurcangal.itirafet.model.Post

class PostAdapter(private val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder>() {

    class PostAdapterViewHolder(val binding : PostDesignBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {
        val binding = PostDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {
        val item = holder.binding
        item.postTextView.text = postList[position].postText
        item.postTime.text = postList[position].date

    }

    override fun getItemCount(): Int {
        return postList.size
    }

}