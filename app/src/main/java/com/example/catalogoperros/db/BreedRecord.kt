package com.example.catalogoperros.db

data class BreedRecord(val breedName: String, val subBreed: String?, val imageURL: String?) {

    constructor(breedName: String) : this(breedName, null, null)

    constructor(breedName: String, subBreed: String?) : this(breedName, subBreed, null)
}
