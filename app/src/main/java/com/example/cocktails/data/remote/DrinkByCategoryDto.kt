package com.example.cocktails.data.remote


import com.squareup.moshi.Json

data class DrinkByCategoryDto(
    @Json(name = "drinks")
    val drinks: List<DrinkCategory>
)