package com.example.catalogoperros.db

import android.provider.BaseColumns

class BreedDatabaseColumns : BaseColumns {
    companion object {
        const val TABLE_NAME = "breeds"
        const val COL_ID = "breed_ID"
        const val COL_BREED = "breed_Name"
        const val COL_SUBBREED = "breed_subBreed"
        const val COL_URL = "breed_URL"
    }
}