package com.ugurcangal.itirafet.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.BaseFragment
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.databinding.FragmentLoginBinding
import com.ugurcangal.itirafet.viewmodel.LoginViewModel
import java.util.*


class LoginFragment : BaseFragment<FragmentLoginBinding,LoginViewModel>(FragmentLoginBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_feedFragment)
        }

        binding.buttonGiris.setOnClickListener {
            viewModel.anonLogin(it)
            Toast.makeText(context,"Hoşgeldiniz :)",Toast.LENGTH_SHORT).show()
        }
        translationAnimation()

    }

    fun translationAnimation(){
        val ta = ObjectAnimator.ofFloat(binding.personImage, "translationY", -1000.0f,0.0f).apply {
            duration = 1200
        }
        ta.start()
    }

    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java


}