package com.example.viewmodeldemo.ui.fragment.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.fragment.vm.SharedViewModel
import kotlinx.android.synthetic.main.fragment_demo02.*
import kotlin.math.log

private const val TAG = "DemoFragment02"

class DemoFragment02 : Fragment() {
    //private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }
    private val viewModel:SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo02, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView4.text = "当前viewModel为$viewModel"
        textView5.text = "当前data为${viewModel.data}"
        Log.e(TAG, "当前viewModel为$viewModel" )
        Log.e(TAG,  "当前data为${viewModel.data}")
    }
    
}