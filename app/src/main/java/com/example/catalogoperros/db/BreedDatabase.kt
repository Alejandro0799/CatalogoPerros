package com.example.catalogoperros.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.catalogoperros.request.RequestFunctions
import java.io.IOException

class BreedDatabase(context: Context) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object {
        const val dbName = "breeds.db"
        const val dbVersion = 1


    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE IF NOT EXISTS ${BreedDatabaseColumns.TABLE_NAME}(" +
                    "${BreedDatabaseColumns.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "${BreedDatabaseColumns.COL_BREED} VARCHAR(255) NOT NULL," +
                    "${BreedDatabaseColumns.COL_SUBBREED} VARCHAR(255)," +
                    "${BreedDatabaseColumns.COL_URL} VARCHAR(255) NOT NULL" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS ${BreedDatabaseColumns.TABLE_NAME}")
        onCreate(db)

    }

    fun loadDataIntoDB() {

        val breedList = RequestFunctions.getBreedList()
        val completeBreedList = ArrayList<BreedRecord>()

        for (breed in breedList) {
            RequestFunctions.getBreedImages(breed).forEach { url ->
                completeBreedList.add(
                    BreedRecord(
                        breed.breedName,
                        breed.subBreed,
                        url
                    )
                )
            }
        }

        insertAllBreeds(completeBreedList)

    }

    private fun insertAllBreeds(breedList: List<BreedRecord>) {

        val db = this.writableDatabase

        for (breed in breedList) {
            try {
                insertBreed(breed, db)

            } catch (e: IOException) {
                Log.e("Insert error", "No se pudo insertar el registro")

            }
        }

        db.close()

    }

    private fun insertBreed(breed: BreedRecord, db: SQLiteDatabase) {

        val record = ContentValues()

        record.put(BreedDatabaseColumns.COL_BREED, breed.breedName)
        record.put(BreedDatabaseColumns.COL_SUBBREED, breed.subBreed)
        record.put(BreedDatabaseColumns.COL_URL, breed.imageURL)

        db.insert(BreedDatabaseColumns.TABLE_NAME, null, record)

    }

    fun selectAllBreedImages(breedName: String, subBreed: String?): List<String> {

        val db = this.readableDatabase
        val breedImageList = ArrayList<String>()
        val columns = arrayOf(BreedDatabaseColumns.COL_URL)
        val whereClause: String
        val whereArgs: Array<String>

        if (subBreed != null) {
            whereClause =
                "${BreedDatabaseColumns.COL_BREED} = ? AND ${BreedDatabaseColumns.COL_SUBBREED} = ?"
            whereArgs = arrayOf(breedName, subBreed)

        } else {
            whereClause = "${BreedDatabaseColumns.COL_BREED} = ?"
            whereArgs = arrayOf(breedName)

        }

        val cursor: Cursor = db.query(
            BreedDatabaseColumns.TABLE_NAME,
            columns,
            whereClause,
            whereArgs,
            null,
            null,
            BreedDatabaseColumns.COL_ID
        )

        while (cursor.moveToNext()) {
            val breedURL =
                cursor.getString(cursor.getColumnIndexOrThrow(BreedDatabaseColumns.COL_URL))

            breedImageList.add(breedURL)
        }

        cursor.close()

        return breedImageList

    }

    fun selectDistinctBreeds(): List<BreedRecord> {

        val breedList = ArrayList<BreedRecord>()
        val db = this.readableDatabase
        val columns = arrayOf(BreedDatabaseColumns.COL_BREED, BreedDatabaseColumns.COL_SUBBREED)

        val cursor: Cursor = db.query(
            BreedDatabaseColumns.TABLE_NAME,
            columns,
            null,
            null,
            "${BreedDatabaseColumns.COL_BREED},${BreedDatabaseColumns.COL_SUBBREED}",
            null,
            null
        )

        while (cursor.moveToNext()) {
            val breedName =
                cursor.getString(cursor.getColumnIndexOrThrow(BreedDatabaseColumns.COL_BREED))
            val subBreed =
                cursor.getString(cursor.getColumnIndexOrThrow(BreedDatabaseColumns.COL_SUBBREED))

            breedList.add(BreedRecord(breedName, subBreed))

        }

        cursor.close()
        db.close()

        return breedList

    }

    fun databaseExists(context: Context): Boolean {
        val file = context.getDatabasePath(dbName)
        return file.exists()

    }
}