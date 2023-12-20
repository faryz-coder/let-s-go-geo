package com.bmh.letsgogeonative.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmh.letsgogeonative.databinding.CarouselLayoutBinding
import com.bmh.letsgogeonative.model.Constant
import com.squareup.picasso.Picasso

class EventAdapter(private val event: MutableList<Constant.Event>) :
    RecyclerView.Adapter<EventAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: CarouselLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CarouselLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageView = holder.binding.carouselImageView
        val imageUrl = event[position].imgUrl
        if (imageUrl.isNotEmpty()) {
            Picasso.get().load(imageUrl).into(imageView)

        }
    }
}