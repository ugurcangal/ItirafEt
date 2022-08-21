package com.ugurcangal.itirafet.viewmodel

import android.text.format.DateFormat
import android.text.style.TtsSpan
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.type.DateTime
import com.google.type.TimeOfDay
import com.ugurcangal.itirafet.R
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel : ViewModel() {

    val firestore = Firebase.firestore
    val auth = Firebase.auth

    fun uploadPost(text : String){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        val postMap = hashMapOf<String,Any>()
        postMap.put("postText", text)
        postMap.put("date", currentDate)
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