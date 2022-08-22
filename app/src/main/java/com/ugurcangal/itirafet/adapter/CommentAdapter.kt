package com.ugurcangal.itirafet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugurcangal.itirafet.databinding.CommentDesignBinding
import com.ugurcangal.itirafet.model.Comment


class CommentAdapter(private var commentList: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentAdapterViewHolder>()  {

    class CommentAdapterViewHolder(val binding : CommentDesignBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapterViewHolder {
        val binding = CommentDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapterViewHolder, position: Int) {
        val item = holder.binding
        item.commentTextView.text = commentList[position].commentText
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun setCommentList(newList: ArrayList<Comment>){
        this.commentList = newList
        notifyDataSetChanged()
    }
}