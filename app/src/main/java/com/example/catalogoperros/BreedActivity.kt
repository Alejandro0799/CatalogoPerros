package com.example.catalogoperros

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.catalogoperros.coroutines.ImageCoroutine
import com.example.catalogoperros.db.BreedDatabase
import com.example.catalogoperros.db.BreedRecord
import com.example.catalogoperros.adapters.viewpager.ViewpagerAdapter

class BreedActivity : AppCompatActivity() {

    private lateinit var scope: ImageCoroutine
    private lateinit var breedDB: BreedDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed)

        val breedName = intent.getStringExtra(MainActivity.BREED_NAME)
        val subBreed = intent.getStringExtra(MainActivity.BREED_SUBBREED)

        val tb = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = if (subBreed != null) {
            "$subBreed $breedName"
        } else {
            "$breedName"
        }

        breedDB = BreedDatabase(this)
        scope = ImageCoroutine(breedDB)

        val viewPager = findViewById<ViewPager2>(R.id.vpImages)
        viewPager.adapter = ViewpagerAdapter(ArrayList(), this)
        scope.loadImages(viewPager, BreedRecord(breedName!!, subBreed))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancelJobs()
    }
}