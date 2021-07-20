package com.example.viewmodeldemo.ui.activity.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.activity.vm.DemoAndroidViewModel
import kotlinx.android.synthetic.main.activity_android_view_model.*

class AndroidViewModelActivity : AppCompatActivity() {
    //private val viewModel: DemoAndroidViewModel by lazy { ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application)).get(DemoAndroidViewModel::class.java) }
    private val viewModel:DemoAndroidViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_view_model)
        setListeners()
    }

    private fun setListeners() {
        jump_button_android_view_model.setOnClickListener {
            startActivity(Intent(this,SharedViewModelActivity::class.java))
        }
    }
}