package com.bmh.letsgogeonative.ui.list_topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmh.letsgogeonative.databinding.RowTopicBinding

class TopicAdapter(private val sections: MutableList<Sections>) :
    RecyclerView.Adapter<TopicAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowTopicBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowTopicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val btn_topic = holder.binding.btnTopic
        btn_topic.text = sections[position].topic
    }

}