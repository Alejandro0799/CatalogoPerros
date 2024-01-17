package com.example.catalogoperros.adapters.recyclerView

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.catalogoperros.BreedActivity
import com.example.catalogoperros.MainActivity
import com.example.catalogoperros.R
import com.example.catalogoperros.db.BreedRecord

class BreedViewHolder(itemView: View) : ViewHolder(itemView) {

    fun showItem(breed: BreedRecord, position: Int) {
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        val tvBreedName: TextView = itemView.findViewById(R.id.tvBreedName)
        val tvSubBreed: TextView = itemView.findViewById(R.id.tvSubBreed)
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)

        tvNumber.text = position.toString()
        tvBreedName.text = breed.breedName
        tvSubBreed.text = breed.subBreed ?: ""

        Glide.with(MainActivity.appContext)
            .load(breed.imageURL)
            .into(ivImage)

        itemView.setOnClickListener {
            showBreedImages(breed)
        }
    }

    private fun showBreedImages(breed: BreedRecord) {
        val intent = Intent(MainActivity.appContext, BreedActivity::class.java)
        intent.putExtra(MainActivity.BREED_NAME, breed.breedName)
        intent.putExtra(MainActivity.BREED_SUBBREED, breed.subBreed)

        startActivity(MainActivity.appContext, intent, null)

    }
}