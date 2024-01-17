package com.example.catalogoperros

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoperros.coroutines.DatabaseCoroutine
import com.example.catalogoperros.db.BreedDatabase
import com.example.catalogoperros.adapters.recyclerView.BreedRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var appContext: Context
        const val BREED_NAME = "KEY_BREED"
        const val BREED_SUBBREED = "KEY_SUBBREED"

    }

    private lateinit var breedDB: BreedDatabase
    private lateinit var scope: DatabaseCoroutine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appContext = this
        breedDB = BreedDatabase(this)

        val tb = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(tb)

        val recyclerView = findViewById<RecyclerView>(R.id.rvBreedList)
        scope = DatabaseCoroutine(breedDB)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BreedRecyclerViewAdapter(ArrayList())
        scope.loadData(recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancelJobs()
    }
}