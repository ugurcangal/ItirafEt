package com.ugurcangal.itirafet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.databinding.PostDesignBinding
import com.ugurcangal.itirafet.model.Post
import com.ugurcangal.itirafet.view.FeedFragment
import com.ugurcangal.itirafet.view.FeedFragmentDirections
import com.ugurcangal.itirafet.view.PostFragment
import com.ugurcangal.itirafet.view.bottomsheet.FeedBottomSheetFragment

class PostAdapter(
    private var postList: ArrayList<Post>,
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
        item.likeCount.text = postList[position].likeCount

        val firestore : FirebaseFirestore = Firebase.firestore
        val auth : FirebaseAuth = Firebase.auth

        var liker = ArrayList<String>()

        firestore.collection("Posts").document(postList[position].id).addSnapshotListener { value, error ->
            liker = value?.get("liker") as ArrayList<String>
            if (liker.contains(auth.currentUser?.uid)){
                item.postLike.setImageResource(R.drawable.ic_like_dolu)
            }else{
                item.postLike.setImageResource(R.drawable.ic_like)
            }
        }


        item.postLike.setOnClickListener {
            if (liker.contains(auth.currentUser?.uid)){
                liker.remove(auth.currentUser!!.uid)
                item.postLike.setImageResource(R.drawable.ic_like)
                firestore.collection("Posts").document(postList[position].id).update("likeCount",postList[position].likeCount.toInt()-1)
                firestore.collection("Posts").document(postList[position].id).update("liker",liker)
            }else{
                liker.add(auth.currentUser!!.uid)
                item.postLike.setImageResource(R.drawable.ic_like_dolu)
                firestore.collection("Posts").document(postList[position].id).update("liker", liker)
                firestore.collection("Posts").document(postList[position].id).update("likeCount",postList[position].likeCount.toInt()+1)
            }
        }



        item.postComment.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToFeedBottomSheetFragment(postList[position].id)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setList(newList: ArrayList<Post>) {
        this.postList = newList
    }

}