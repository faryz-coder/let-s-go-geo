package com.bmh.letsgogeonative.ui.list_topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bmh.letsgogeonative.model.Constant

class ListTopicViewModel: ViewModel() {
    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text */

    private val _section = MutableLiveData<MutableList<Sections>>()

    private val _topicContent = MutableLiveData<MutableList<Constant.TopicContent>>()

    private var _setsQuestion = MutableLiveData<Constant.Question>()

    val section: LiveData<MutableList<Sections>> = _section
    val topicContent: LiveData<MutableList<Constant.TopicContent>> = _topicContent
    val setsQuestion: LiveData<Constant.Question> = _setsQuestion

    var topicSelected: String = ""
    var sectionSelected: String = ""

    fun setList(item: MutableList<Sections>) {
        _section.value?.clear()
        _section.value = item
    }

    fun setTopicContent(item: MutableList<Constant.TopicContent>) {
        _topicContent.value?.clear()
        _topicContent.value = item
    }

    fun setQuestion(item: Constant.Question) {
        _setsQuestion.value = item
    }
}