package com.example.navigationdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var notificationId:Int = 0
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初始化NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController= navHostFragment.navController


        Log.e(TAG, "$navController" )


        //让Toolbar替换掉Actionbar
        setSupportActionBar(toolbar)

        //Toolbar+Navigation
        navigationToolbar()

        //DrawerLayout + Navigation
        navigationDrawer()

        //BottomNavigation
        bottomNavigation()

    }

    private fun bottomNavigation() {
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun navigationDrawer() {
        navigation_view.setupWithNavController(navController)
    }


    private fun getAppBarConfiguration():AppBarConfiguration= AppBarConfiguration(setOf(R.id.demoFragment01,R.id.secondPageFragment,R.id.thirdPageFragment)
    ,drawer_layout,::onSupportNavigateUp)
        /*AppBarConfiguration(navGraph = navController.graph
            ,drawerLayout = drawer_layout
            ,fallbackOnNavigateUpListener = ::onSupportNavigateUp)*/

    //Navigation With Toolbar
    private fun navigationToolbar() {

        toolbar.setupWithNavController(navController,getAppBarConfiguration())

        //本来想试试改改右上角的 drawer菜单图标的，好像效果不是很好。
        //这玩意已近被Navigation接管了，设置了也意义不大
        //toolbar.setNavigationIcon(R.drawable.ic_android_black_24dp)

        //toolbar上的ImageView默认是没有的
        //toolbar.setLogo(R.drawable.ic_launcher_background)

        //toolbar上的TextView默认也是没有的。
        //toolbar.title = "Title"(这玩意已近被Navigation接管了，所以设置了也意义不大)
        //toolbar.subtitle = "SubTitle"
    }

    override fun onPause() {
        super.onPause()
        val manager = NotificationManagerCompat.from(this)
        manager.notify(notificationId++,createNotification())
    }

    //创建Notification
    private fun createNotification(): Notification {
        val notificationName = packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationName, "DeepLinkChanner",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
        return NotificationCompat.Builder(this, notificationName)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("这是DeepLink Demo")
            .setContentText("DeepLink")
            .setContentIntent(getPendingIntent())
            .setAutoCancel(true)
            .build()
    }


    //创建一个PendingIntent
    private fun getPendingIntent(): PendingIntent {
        return NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_demo)
            //setComponentName感觉很鸡肋，又没太懂他是干啥的。
            //.setComponentName(DeepLinkActivity::class.java)
            .setDestination(R.id.deepLinkActivity)
            .createPendingIntent()
    }

    //创建Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //利用Navigation对MenuItem进行导航
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


}