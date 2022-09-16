package com.ugurcangal.itirafet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import java.lang.IllegalArgumentException

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>(
    private val bindingInflater : (inflater : LayoutInflater) -> VB
) : Fragment() {

    private var _binding : VB? = null
    val binding : VB
    get() = _binding as VB
    lateinit var viewModel : VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if(_binding == null)
            throw  IllegalArgumentException("Binding cannot be null")
        viewModel = ViewModelProvider(this)[getViewModel()]

        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

}