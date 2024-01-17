package com.example.catalogoperros.request

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

data class BreedImages(
    val message: List<String>
)

data class BreedImage(
    val message: String
)