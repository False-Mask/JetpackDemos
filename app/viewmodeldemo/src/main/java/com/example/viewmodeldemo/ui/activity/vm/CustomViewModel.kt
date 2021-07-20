package com.example.viewmodeldemo.ui.activity.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.ui.fragment.model.DemoData

/**
 *@author ZhiQiang Tu
 *@time 2021/7/20  7:34
 *@signature 我们不明前路，却已在路上
 */
private const val TAG = "CustomViewModel"
class CustomViewModel(var data: DemoData) : ViewModel(){
    fun logData(){
        //检测data是否真的被传入了
        Log.e(TAG, "$data")
    }
}


class CustomFactory:ViewModelProvider.Factory{
    //这个方法是ViewModel内部调用创建ViewModel实例的，所以它的任务就只是返回一个ViewModel,你怎么返回它并不关心。
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val data = DemoData(0,"data")
        val customCustomViewModel = CustomViewModel(data)
        return customCustomViewModel as T
    }

}