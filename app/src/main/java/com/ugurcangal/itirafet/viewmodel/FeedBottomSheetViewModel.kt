package com.ugurcangal.itirafet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.model.Comment
import com.ugurcangal.itirafet.model.Post
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class FeedBottomSheetViewModel : ViewModel() {

    var commentArrayList = MutableLiveData<ArrayList<Comment>>()
    private val firestore = Firebase.firestore

    fun uploadComment(commentText : String, postText:String){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        val postMap = hashMapOf<String,Any>()
        postMap.put("postText", postText)
        postMap.put("commentText", commentText)
        postMap.put("date", currentDate)
        firestore.collection("Comments").add(postMap).addOnSuccessListener {

        }.addOnFailureListener {
            it.localizedMessage?.let { it1 -> Log.d("Hata", it1) }
        }
    }

    fun getComment(inCommentPostText: String){
        firestore.collection("Comments").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("getComment Hata" , error.localizedMessage!!.toString())
            }else{
                value?.let {
                    if (!value.isEmpty){
                        val documents = value.documents

                        val commentArrayList2 = ArrayList<Comment>()

                        for (document in documents){
                            if (inCommentPostText == document.get("postText")){
                                val commentText = document.get("commentText") as String
                                val date = document.get("date")

                                val comment = Comment(commentText, date.toString())
                                commentArrayList2.add(comment)
                                commentArrayList.value = commentArrayList2
                            }

                        }
                    }
                }
            }
        }
    }

    fun observeCommentList() : LiveData<ArrayList<Comment>>{
        return commentArrayList
    }

}