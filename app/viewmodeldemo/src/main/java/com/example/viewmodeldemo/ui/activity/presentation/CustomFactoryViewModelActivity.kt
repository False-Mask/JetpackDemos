package com.example.viewmodeldemo.ui.activity.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.activity.vm.CustomFactory
import com.example.viewmodeldemo.ui.activity.vm.CustomViewModel

class CustomFactoryViewModelActivity : AppCompatActivity() {
//    private val viewModel by lazy { ViewModelProvider(this,CustomFactory()).get(CustomViewModel::class.java) }
    private val viewModel:CustomViewModel by viewModels { CustomFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_factory_view_model)
        viewModel.logData()
    }
}