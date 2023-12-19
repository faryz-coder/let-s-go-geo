package com.bmh.letsgogeonative.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bmh.letsgogeonative.model.Constant

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _event = MutableLiveData<MutableList<Constant.Event>>()

    val event: LiveData<MutableList<Constant.Event>> = _event

    private val _announcement = MutableLiveData<MutableList<Constant.Event>>()

    val announcement: LiveData<MutableList<Constant.Event>> = _announcement

    fun setEvent(item: MutableList<Constant.Event>) {
        _event.value?.clear()
        _event.value = item
    }

    fun setAnnouncement(item: MutableList<Constant.Event>) {
        _announcement.value?.clear()
        _announcement.value = item
    }
}