package com.bmh.letsgogeonative.ui.slideshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmh.letsgogeonative.databinding.RowScoreBinding
import com.bmh.letsgogeonative.databinding.RowTopicBinding
import com.bmh.letsgogeonative.model.Constant

class ScoreAdapter(private val score: MutableList<Constant.Score>) :
    RecyclerView.Adapter<ScoreAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowScoreBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowScoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return score.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = score[position]

        holder.binding.scoreTopic.text = item.topic.capitalize()
        holder.binding.scoreResult.text = item.result.toString()
        holder.binding.scoreTotal.text = item.totalQuestion.toString()
    }

}
