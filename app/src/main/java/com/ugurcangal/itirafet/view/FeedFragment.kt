package com.ugurcangal.itirafet.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ugurcangal.itirafet.BaseFragment
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.adapter.PostAdapter
import com.ugurcangal.itirafet.databinding.FragmentFeedBinding
import com.ugurcangal.itirafet.model.Post
import com.ugurcangal.itirafet.viewmodel.FeedViewModel


class FeedFragment : BaseFragment<FragmentFeedBinding,FeedViewModel>(FragmentFeedBinding::inflate) {


    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var postAdapter : PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postArrayList = ArrayList()
        postAdapter = PostAdapter(postArrayList)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePostRecyclerView()
        observePostList()
        viewModel.getPost()

        binding.buttonFAB.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_postFragment)
        }

    }


    private fun preparePostRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }
    }


    private fun observePostList() {
        viewModel.observePostList().observe(viewLifecycleOwner){
            postAdapter.setList(it)
            postAdapter.notifyDataSetChanged()
        }
    }

    override fun getViewModel(): Class<FeedViewModel> = FeedViewModel::class.java


}