package com.example.navigationdemo.ui.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.navigationdemo.R
import com.example.navigationdemo.model.User
private const val TAG = "DemoFragment02"
class DemoFragment02 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val user = arguments?.get("user") as String
        Log.e(TAG, user.toString() )*/

        /*arguments?.let {
            val user = DemoFragment02Args.fromBundle(it).user
            Log.e(TAG, "${user.username} ${user.age}" )
        }*/

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.demo_transition)

        val args:DemoFragment02Args by navArgs()

        val user = args.user
        if (user != null) {
            Log.e(TAG, "${user.username} ${user.age}" )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo02, container, false)
    }
}