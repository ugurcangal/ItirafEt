package com.ugurcangal.itirafet.view.bottomsheet

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ugurcangal.itirafet.R
import com.ugurcangal.itirafet.adapter.CommentAdapter
import com.ugurcangal.itirafet.databinding.FragmentFeedBottomSheetBinding
import com.ugurcangal.itirafet.model.Comment
import com.ugurcangal.itirafet.viewmodel.FeedBottomSheetViewModel


class FeedBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentFeedBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FeedBottomSheetViewModel
    private lateinit var commentArrayList : ArrayList<Comment>
    private lateinit var commentAdapter : CommentAdapter
    private lateinit var postText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FeedBottomSheetViewModel by viewModels()
        viewModel = tempViewModel
        commentArrayList = ArrayList()
        commentAdapter = CommentAdapter(commentArrayList)

        arguments?.let {
            postText = FeedBottomSheetFragmentArgs.fromBundle(it).postText
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBottomSheetBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCommentRecyclerView()
        observeCommentList()
        viewModel.getComment(postText)

        val feedBottomSheetFragment = FeedBottomSheetFragment()


        binding.buttonYorumGonder.setOnClickListener {
            val commentText = binding.commentEditText.text.toString()
            viewModel.uploadComment(commentText,postText)
            Toast.makeText(context,"Yorum Gönderildi!", Toast.LENGTH_SHORT).show()
        }

    }


    private fun prepareCommentRecyclerView(){
        binding.commentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    private fun observeCommentList(){
        viewModel.observeCommentList().observe(viewLifecycleOwner){
            commentAdapter.setCommentList(it)
        }
    }


}