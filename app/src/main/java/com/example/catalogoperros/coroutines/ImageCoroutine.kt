package com.example.catalogoperros.coroutines

import androidx.viewpager2.widget.ViewPager2
import com.example.catalogoperros.db.BreedDatabase
import com.example.catalogoperros.db.BreedRecord
import com.example.catalogoperros.viewpager.ViewpagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageCoroutine(private val breedDB: BreedDatabase) {

    private var job1: Job? = null
    private var job2: Job? = null

    fun loadImages(viewpager: ViewPager2, breed: BreedRecord) {

        job1 = CoroutineScope(Dispatchers.IO).launch {

            val imageList =
                breedDB.selectAllBreedImages(breed.breedName, breed.subBreed) as ArrayList
            showImages(viewpager, imageList)
        }
    }

    private fun showImages(viewpager: ViewPager2, imageList: ArrayList<String>) {

        job2 = CoroutineScope(Dispatchers.Main).launch {
            val viewpagerAdapter = viewpager.adapter as ViewpagerAdapter
            viewpagerAdapter.insertAllImages(imageList)

        }
    }

    fun cancelJobs() {
        job1!!.cancel()
        job2!!.cancel()

    }

}