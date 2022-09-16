package com.ugurcangal.itirafet.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ugurcangal.itirafet.BaseFragment
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.databinding.FragmentPostBinding
import com.ugurcangal.itirafet.viewmodel.PostViewModel

class PostFragment : BaseFragment<FragmentPostBinding,PostViewModel>(FragmentPostBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPaylas.setOnClickListener {
            val postText = binding.postEditText.text.toString()
            if (postText.length < 15){
                Toast.makeText(context,"Lütfen daha uzun bir yazı girin!",Toast.LENGTH_SHORT).show()
            }else if (postText.length > 600){
                Toast.makeText(context,"İtiraf karakter sınırını aşıyor!",Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.uploadPost(postText)
                findNavController().navigate(R.id.action_postFragment_to_feedFragment)
                Toast.makeText(context,"İtiraf Paylaşıldı!",Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonLogout.setOnClickListener{
            viewModel.logOut(it)
        }
    }

    override fun getViewModel(): Class<PostViewModel> = PostViewModel::class.java


}