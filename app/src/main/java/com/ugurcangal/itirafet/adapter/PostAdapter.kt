package com.ugurcangal.itirafet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.ugurcangal.itirafet.databinding.PostDesignBinding
import com.ugurcangal.itirafet.model.Post
import com.ugurcangal.itirafet.view.bottomsheet.FeedBottomSheetFragment

class PostAdapter(
    private var postList: ArrayList<Post>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder>() {

    class PostAdapterViewHolder(val binding: PostDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {
        val binding = PostDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {
        val item = holder.binding
        item.postTextView.text = postList[position].postText
        item.postTime.text = postList[position].date
        item.postComment.setOnClickListener {
            val feedBottomSheetFragment = FeedBottomSheetFragment()
            feedBottomSheetFragment.show(fragmentManager, "CommentSheet")
        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setList(newList: ArrayList<Post>) {
        this.postList = newList
        notifyDataSetChanged()
    }

}