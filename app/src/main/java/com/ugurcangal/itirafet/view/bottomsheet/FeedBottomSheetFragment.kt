package com.ugurcangal.itirafet.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ugurcangal.itirafet.adapter.CommentAdapter
import com.ugurcangal.itirafet.databinding.FragmentFeedBottomSheetBinding
import com.ugurcangal.itirafet.model.Comment
import com.ugurcangal.itirafet.viewmodel.FeedBottomSheetViewModel
import kotlin.collections.ArrayList


class FeedBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentFeedBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FeedBottomSheetViewModel
    private lateinit var commentArrayList : ArrayList<Comment>
    private lateinit var commentAdapter : CommentAdapter
    private lateinit var postId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FeedBottomSheetViewModel by viewModels()
        viewModel = tempViewModel
        commentArrayList = ArrayList()
        commentAdapter = CommentAdapter(commentArrayList)

        arguments?.let {
            postId = FeedBottomSheetFragmentArgs.fromBundle(it).postId
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
        viewModel.getComment(postId)


        binding.buttonYorumGonder.setOnClickListener {
            val commentText = binding.commentEditText.text.toString()
            if (commentText.isNotEmpty()){
                viewModel.uploadComment(commentText,postId)
                Toast.makeText(context,"Yorum Gönderildi!", Toast.LENGTH_SHORT).show()
                binding.commentEditText.text.clear()
            }else{
                Toast.makeText(context,"Lütfen önce yorum yazın!", Toast.LENGTH_SHORT).show()
            }
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
            binding.yorumlarText.text = "Yorumlar : ${it.size}"
        }
    }


}