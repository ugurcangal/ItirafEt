package com.ugurcangal.itirafet.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.model.Post

class FeedViewModel : ViewModel() {

    var postArrayList = MutableLiveData<ArrayList<Post>>()
    private val firestore = Firebase.firestore

    fun getPost(){
        firestore.collection("Posts").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("getPost Hata" , error.localizedMessage!!.toString())
            }else{
                value?.let {
                    if (!value.isEmpty){
                        val documents = value.documents

                        val postArrayList2 = ArrayList<Post>()

                        for (document in documents){

                            val id = document.id
                            val postText = document.get("postText") as String
                            val date = document.get("date")

                            val post = Post(id,postText, date.toString())
                            postArrayList2.add(post)
                            postArrayList.value = postArrayList2
                        }
                    }
                }
            }
        }
    }

    fun observePostList() : LiveData<ArrayList<Post>>{
        return postArrayList
    }

}