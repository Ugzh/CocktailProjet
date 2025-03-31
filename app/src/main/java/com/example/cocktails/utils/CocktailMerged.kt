package com.example.cocktails.utils

import java.util.UUID

data class CocktailMerged(
    val id: Int?,
    val uuid: UUID?,
    val title: String,
    val imageUrl: String,
    val ingredients: String,
    val instructions: String
)
