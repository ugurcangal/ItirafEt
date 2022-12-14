package com.ugurcangal.itirafet.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.R

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth

    fun anonLogin(view: View) {
        auth.signInAnonymously()
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("Giriş", "signInAnonymously:success")
                    Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_feedFragment)
                }
            }
    }

}