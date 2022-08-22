package com.ugurcangal.itirafet.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.adapter.PostAdapter
import com.ugurcangal.itirafet.databinding.FragmentFeedBinding
import com.ugurcangal.itirafet.model.Post
import com.ugurcangal.itirafet.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var postAdapter : PostAdapter
    private lateinit var viewModel : FeedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel : FeedViewModel by viewModels()
        viewModel = tempViewModel
        postArrayList = ArrayList()
        postAdapter = PostAdapter(postArrayList,childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        preparePostRecyclerView()
        observePostList()
        viewModel.getPost()

        binding.buttonFAB.setOnClickListener {
            viewModel.newPost(it)
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


    private fun menuFun(){
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.post_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.yeniGonderi -> {
                        findNavController().navigate(R.id.action_feedFragment_to_postFragment)
                    }
                }
                return true
            }
        })
    }

}