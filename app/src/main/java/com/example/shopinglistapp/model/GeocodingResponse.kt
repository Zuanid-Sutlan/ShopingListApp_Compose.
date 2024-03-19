package com.example.shopinglistapp.model

data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)