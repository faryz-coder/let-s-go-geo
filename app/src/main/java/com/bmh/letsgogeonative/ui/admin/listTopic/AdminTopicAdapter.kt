package com.bmh.letsgogeonative.ui.admin.listTopic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmh.letsgogeonative.databinding.AdminTopicRowBinding
import com.bmh.letsgogeonative.ui.list_topic.Sections
import kotlin.reflect.KFunction1

class AdminTopicAdapter(private val sections: MutableList<Sections>, val onSelection: KFunction1<String, Unit>) :
    RecyclerView.Adapter<AdminTopicAdapter.ViewHolder>() {
    class ViewHolder(val binding: AdminTopicRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdminTopicRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = sections[position]

        holder.binding.topicName.text = item.topic
        holder.binding.deleteTopic.setOnClickListener {
            onSelection(sections[position].topic)
        }
    }

}
