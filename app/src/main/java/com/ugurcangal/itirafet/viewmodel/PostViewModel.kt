package com.ugurcangal.itirafet.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.R
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel : ViewModel() {

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    fun uploadPost(text : String){

        val sdf = SimpleDateFormat("dd/M/yyyy HH:mm")
        val currentDate = sdf.format(Date())
        val postMap = hashMapOf<String,Any>()
        val likerList = arrayListOf<String>()
        postMap.put("postText", text)
        postMap.put("date", currentDate)
        postMap.put("likeCount", 0)
        postMap.put("liker", likerList)
        firestore.collection("Posts").add(postMap).addOnSuccessListener {

        }.addOnFailureListener {
            it.localizedMessage?.let { it1 -> Log.d("Hata", it1) }
        }
    }

    fun logOut(view: View){
        auth.signOut()
        Navigation.findNavController(view).navigate(R.id.action_postFragment_to_loginFragment)
    }
}