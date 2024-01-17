package com.example.catalogoperros.adapters.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoperros.R

class ViewpagerAdapter(private val imageList: ArrayList<String>, val context: Context) :
    RecyclerView.Adapter<ViewpagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewpagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewpagerViewHolder(
            layoutInflater.inflate(
                R.layout.viewpager_item, parent, false
            ), context
        )
    }

    override fun onBindViewHolder(holder: ViewpagerViewHolder, position: Int) {
        val image = imageList[position]
        holder.show(image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun insertAllImages(newImageList: ArrayList<String>) {
        val startValue = imageList.size
        imageList.addAll(newImageList)

        notifyItemRangeInserted(startValue, newImageList.size)

    }
}