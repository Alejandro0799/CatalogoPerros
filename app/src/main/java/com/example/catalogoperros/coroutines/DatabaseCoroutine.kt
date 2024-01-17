package com.example.catalogoperros.coroutines

import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoperros.MainActivity
import com.example.catalogoperros.db.BreedDatabase
import com.example.catalogoperros.db.BreedRecord
import com.example.catalogoperros.recyclerView.BreedRecyclerViewAdapter
import com.example.catalogoperros.request.RequestFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DatabaseCoroutine(private val breedDB: BreedDatabase) {

    private var job1: Job? = null
    private var job2: Job? = null

    fun loadData(recyclerView: RecyclerView) {

        job1 = CoroutineScope(Dispatchers.IO).launch {

            //Si no existe, se crea la base de datos
            if (!breedDB.databaseExists(MainActivity.appContext)) {
                breedDB.loadDataIntoDB()
            }

            val breedList = breedDB.selectDistinctBreeds()
            val breedListWithImages = ArrayList<BreedRecord>()

            breedList.forEach { breed ->
                val imageUrl = RequestFunctions.getRandomImage(breed)
                breedListWithImages.add(BreedRecord(breed.breedName, breed.subBreed, imageUrl))
            }

            showData(recyclerView, breedListWithImages)
        }
    }

    private fun showData(recyclerView: RecyclerView, breedList: ArrayList<BreedRecord>) {

        job2 = CoroutineScope(Dispatchers.Main).launch {
            val recyclerViewAdapter = recyclerView.adapter as BreedRecyclerViewAdapter
            recyclerViewAdapter.insertAllRecords(breedList)

        }
    }

    fun cancelJobs() {
        job1!!.cancel()
        job2!!.cancel()

    }

}