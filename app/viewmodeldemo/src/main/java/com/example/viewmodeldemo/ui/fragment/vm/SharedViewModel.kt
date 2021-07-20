package com.example.viewmodeldemo.ui.fragment.vm

import androidx.lifecycle.ViewModel
import com.example.viewmodeldemo.ui.fragment.model.DemoData

/**
 *@author ZhiQiang Tu
 *@time 2021/7/19  21:46
 *@signature 我们不明前路，却已在路上
 */
class SharedViewModel: ViewModel() {
    //测试
    var data:DemoData = DemoData(0,"data")
}

