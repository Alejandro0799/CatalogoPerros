package com.example.catalogoperros.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoperros.R
import com.example.catalogoperros.db.BreedRecord

class BreedRecyclerViewAdapter(private val breedList: ArrayList<BreedRecord>) :
    RecyclerView.Adapter<BreedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BreedViewHolder(layoutInflater.inflate(R.layout.breed_item, parent, false))

    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val item = breedList[position]
        holder.showItem(item, position + 1)

    }

    override fun getItemCount(): Int {
        return breedList.size

    }

    fun insertAllRecords(newBreedList: ArrayList<BreedRecord>) {
        val startValue = breedList.size
        breedList.addAll(newBreedList)

        notifyItemRangeInserted(startValue, newBreedList.size)

    }
}