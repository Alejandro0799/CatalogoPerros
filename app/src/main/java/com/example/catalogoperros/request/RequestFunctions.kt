package com.example.catalogoperros.request

import android.util.Log
import com.example.catalogoperros.db.BreedRecord
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RequestFunctions {


    companion object {

        private val gson: Gson = Gson()

        private const val urlListAll = "https://dog.ceo/api/breeds/list/all"
        private const val rootURL = "https://dog.ceo/api/breed/"

        fun getBreedList(): List<BreedRecord> {
            val url = URL(urlListAll)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = StringBuilder()

                try {
                    val stream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(stream))
                    var line = ""

                    while (reader.readLine().also {
                            if (it != null) line = it
                        } != null) {
                        response.append(line)

                    }
                } catch (e: IOException) {
                    Log.e("URL Error", e.printStackTrace().toString())

                } finally {
                    connection.disconnect()

                }
                return breedsJsonToClass(response.toString())

            } else {
                connection.disconnect()
                return ArrayList()

            }
        }

        fun getBreedImages(breed: BreedRecord): List<String> {

            val url = if (breed.subBreed == null) {
                URL("${rootURL + breed.breedName}/images")
            } else {
                URL("${rootURL + breed.breedName}/${breed.subBreed}/images")
            }

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = StringBuilder()

                try {
                    val stream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(stream))
                    var line = ""

                    while (reader.readLine().also {
                            if (it != null) line = it
                        } != null) {
                        response.append(line)

                    }
                } catch (e: IOException) {
                    Log.e("URL Error", e.printStackTrace().toString())

                } finally {
                    connection.disconnect()

                }
                return breedImagesJsonToClass(response.toString())

            } else {
                connection.disconnect()
                return ArrayList()

            }
        }

        fun getRandomImage(breed: BreedRecord): String {

            val urlString: String

            if (breed.subBreed == null) {
                urlString = "${breed.breedName}/images/random"
            } else {
                urlString = "${breed.breedName}/${breed.subBreed}/images/random"
            }

            val url = URL(rootURL + urlString)

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = StringBuilder()

                try {
                    val stream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(stream))
                    var line = ""

                    while (reader.readLine().also {
                            if (it != null) line = it
                        } != null) {
                        response.append(line)

                    }
                } catch (e: IOException) {
                    Log.e("URL Error", e.printStackTrace().toString())

                } finally {
                    connection.disconnect()

                }

                return gson.fromJson(response.toString(), BreedImage::class.java).message

            } else {
                connection.disconnect()
                return ""

            }

        }


        private fun breedsJsonToClass(json: String): List<BreedRecord> {

            val response = gson.fromJson(json, BreedsResponse::class.java)
            val breedList = ArrayList<BreedRecord>()

            for (record in response.message) {
                val breedName = record.key

                //Si el array de subtipo no esta vacio, se añade un tipo a la lista por cada uno
                if (record.value.isNotEmpty()) {
                    record.value.forEach { subBreed ->
                        breedList.add(BreedRecord(breedName, subBreed, null))
                    }

                } else {
                    breedList.add(BreedRecord(breedName))

                }
            }

            return breedList

        }


        private fun breedImagesJsonToClass(json: String): List<String> {
            val response = gson.fromJson(json, BreedImages::class.java)

            return if (response.message.size >= 10) {
                response.message.subList(0, 10)
            } else {
                response.message
            }

        }
    }
}