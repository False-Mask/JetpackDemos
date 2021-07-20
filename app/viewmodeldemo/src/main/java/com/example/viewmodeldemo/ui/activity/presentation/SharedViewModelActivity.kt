package com.example.viewmodeldemo.ui.activity.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.fragment.presentation.DemoFragment01
import com.example.viewmodeldemo.ui.fragment.presentation.DemoFragment02
import kotlinx.android.synthetic.main.activity_shared_view_model.*

class SharedViewModelActivity : AppCompatActivity() {
    private val demoFragment01 = DemoFragment01()
    private val demoFragment02 = DemoFragment02()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_view_model)
        setListeners()
    }

    private fun setListeners() {
        fragment01_button.setOnClickListener {
            beginTransaction(demoFragment01,demoFragment02)
        }

        fragment02_button.setOnClickListener {
            beginTransaction(demoFragment02,demoFragment01)
        }
        jump_button_shared_view_model.setOnClickListener {
            startActivity(Intent(this,CustomFactoryViewModelActivity::class.java))
        }
    }

    private fun beginTransaction(fragment: Fragment,fragmentOld: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragmentOld).replace(R.id.fragmentContainerView,fragment).commit()
    }
}