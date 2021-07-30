package com.example.navigationdemo.ui.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.AppBarConfiguration
import com.example.navigationdemo.R
import com.example.navigationdemo.model.User
import com.example.navigationdemo.ui.vm.TestViewModel
import kotlinx.android.synthetic.main.fragment_demo01.*
private const val TAG = "DemoFragment01"
class DemoFragment01 : Fragment() {
    //val viewModel:TestViewModel by navGraphViewModels(R.id.nav_demo)
    private var notificationId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        //setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //告诉Toolbar这个地方是有Menu的 。

        return inflater.inflate(R.layout.fragment_demo01, container, false)
    }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        demo01_jump_button.setOnClickListener {
            /*val bundle = Bundle().also {
                it.putSerializable("user",User("tr",19))
            }
            findNavController().navigate(R.id.demoFragment02,bundle)*/

            val imageTransaction = Pair<View,String>(imageView,"demoImage")
            val extras = FragmentNavigatorExtras(imageTransaction)
            val action =
                DemoFragment01Directions.actionDemoFragment01ToDemoFragment022(User("tr", 19))
            findNavController().navigate(action,extras)
            listOf<User>()
        }

        deep_link_button.setOnClickListener {
            val manager = NotificationManagerCompat.from(requireContext())
            manager.notify(notificationId++,createNotification())

        }

    }

    //创建Notification
    private fun createNotification(): Notification {
        val notificationName = requireActivity().packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationName, "DeepLinkChanner",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
        return NotificationCompat.Builder(requireContext(), notificationName)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("这是DeepLink Demo")
            .setContentText("DeepLink")
            .setContentIntent(getPendingIntent())
            .setAutoCancel(true)
            .build()
    }


    //创建一个PendingIntent
    private fun getPendingIntent(): PendingIntent {
            return findNavController()
                .createDeepLink()
                .setDestination(R.id.deepLinkActivity)
                .createPendingIntent()
        /*NavDeepLinkBuilder(requireActivity())
            .setGraph(R.navigation.nav_demo)
                //setComponentName感觉很鸡肋，又没太懂他是干啥的。
            //.setComponentName(DeepLinkActivity::class.java)
            .setDestination(R.id.deepLinkActivity)
            .createPendingIntent()*/
    }

}