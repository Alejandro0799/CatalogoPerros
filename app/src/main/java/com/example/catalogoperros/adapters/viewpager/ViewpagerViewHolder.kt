package com.example.catalogoperros.adapters.viewpager

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.catalogoperros.R

class ViewpagerViewHolder(itemView: View, private val context: Context) : ViewHolder(itemView) {

    fun show(image: String) {
        val imageview = itemView.findViewById<ImageView>(R.id.iv_ViewPager)

        Glide.with(context)
            .load(image)
            .into(imageview)

    }
}