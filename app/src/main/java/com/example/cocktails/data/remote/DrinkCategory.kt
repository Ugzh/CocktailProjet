package com.example.cocktails.data.remote


import com.squareup.moshi.Json

data class DrinkCategory(
    @Json(name = "strDrink")
    val strDrink: String,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String,
    @Json(name = "idDrink")
    val idDrink: String
)