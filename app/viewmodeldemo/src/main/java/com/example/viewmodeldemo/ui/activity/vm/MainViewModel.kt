package com.example.viewmodeldemo.ui.activity.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 *@author ZhiQiang Tu
 *@time 2021/7/19  11:18
 *@signature 我将追寻并获取我想要的答案
 */
private val TAG = "MainViewModel"
class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var number:Long = if (savedStateHandle.contains("number")) {
        savedStateHandle.get<Long>("number") !!
    }
    else {
        savedStateHandle.set("number",0)
        0
    }

    fun plusOne(){
        number++
        updateSavedStateHandle(number)
    }

    private fun updateSavedStateHandle(number: Long) {
        savedStateHandle.set("number",number)
    }

    fun plusTwo(){
        number+=2
        updateSavedStateHandle(number)
    }
}