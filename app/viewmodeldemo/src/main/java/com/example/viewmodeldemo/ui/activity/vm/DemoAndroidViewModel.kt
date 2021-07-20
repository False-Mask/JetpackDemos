package com.example.viewmodeldemo.ui.activity.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 *@author ZhiQiang Tu
 *@time 2021/7/19  21:06
 *@signature 我们不明前路，却已在路上
 */
class DemoAndroidViewModel(application: Application) : AndroidViewModel(application) {
    val mApplication = getApplication<Application>()

}