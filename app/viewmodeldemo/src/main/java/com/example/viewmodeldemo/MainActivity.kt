package com.example.viewmodeldemo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.ui.activity.presentation.AndroidViewModelActivity
import com.example.viewmodeldemo.ui.activity.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //懒加载ViewModel的实例
    //private val viewModel: MainViewModel by lazy { ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java) }
    //private val viewModel:MainViewModel by viewModels()

    private val viewModel:MainViewModel by viewModels{ SavedStateViewModelFactory(application,this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*savedInstanceState?.let {
            viewModel.number = (it.get("number") as String).toLong()
        }*/
        initView()
        setListener()
    }

    private fun setListener() {
        //这种写法可能会出现空指针，建议使用ViewBinding/DataBinding
        plus_one.setOnClickListener {
            viewModel.plusOne()
            updateTextView()
        }

        plus_two.setOnClickListener {
            viewModel.plusTwo()
            updateTextView()
        }

        jump_button.setOnClickListener {
            startActivity(Intent(this, AndroidViewModelActivity::class.java))
        }
    }

    private fun updateTextView() {
        show_number.text = viewModel.number.toString()
    }

    private fun initView() {
        updateTextView()
    }

   /* override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("number", show_number.text.toString())
    }*/
}