package com.ugurcangal.itirafet.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.databinding.FragmentPostBinding
import com.ugurcangal.itirafet.viewmodel.PostViewModel

class PostFragment : Fragment() {

    private var _binding : FragmentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : PostViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPaylas.setOnClickListener {
            val postText = binding.postEditText.text.toString()
            viewModel.uploadPost(postText)
            findNavController().navigate(R.id.action_postFragment_to_feedFragment)
            Toast.makeText(context,"İtiraf Paylaşıldı!",Toast.LENGTH_SHORT).show()
        }

        binding.buttonLogout.setOnClickListener{
            viewModel.logOut(it)
        }
    }


}