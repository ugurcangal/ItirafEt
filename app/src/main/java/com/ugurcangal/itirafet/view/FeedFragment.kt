package com.ugurcangal.itirafet.view

import android.os.Bundle
import android.util.Log
import android.view.*
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
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel : FeedViewModel by viewModels()
        viewModel = tempViewModel
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

        postArrayList = ArrayList()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(postArrayList)
        binding.recyclerView.adapter = postAdapter
        getPost()

        menuFun()

    }

    fun getPost(){
        firestore.collection("Posts").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("getPost Hata" , error.localizedMessage!!.toString())
            }else{
                value?.let {
                    if (!value.isEmpty){
                        val documents = value.documents

                        postArrayList.clear()

                        for (document in documents){

                            val postText = document.get("postText") as String
                            val date = document.get("date")

                            val post = Post(postText, date.toString())
                            postArrayList.add(post)

                        }
                        postAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }




    fun menuFun(){
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