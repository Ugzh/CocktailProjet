package com.example.cocktails.data.remote


import com.example.cocktails.utils.DrinkMapped
import com.squareup.moshi.Json

data class DrinkDto(
    @Json(name = "idDrink")
    val idDrink: String,
    @Json(name = "strDrink")
    val strDrink: String?,
    @Json(name = "strDrinkAlternate")
    val strDrinkAlternate: String?,
    @Json(name = "strTags")
    val strTags: String?,
    @Json(name = "strVideo")
    val strVideo: String?,
    @Json(name = "strCategory")
    val strCategory: String?,
    @Json(name = "strIBA")
    val strIBA: String?,
    @Json(name = "strAlcoholic")
    val strAlcoholic: String?,
    @Json(name = "strGlass")
    val strGlass: String?,
    @Json(name = "strInstructions")
    val strInstructions: String?,
    @Json(name = "strInstructionsES")
    val strInstructionsES: String?,
    @Json(name = "strInstructionsDE")
    val strInstructionsDE: String?,
    @Json(name = "strInstructionsFR")
    val strInstructionsFR: String?,
    @Json(name = "strInstructionsIT")
    val strInstructionsIT: String?,
    @Json(name = "strInstructionsZH-HANS")
    val strInstructionsZHHANS: String?,
    @Json(name = "strInstructionsZH-HANT")
    val strInstructionsZHHANT: String?,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String?,
    @Json(name = "strIngredient1")
    val strIngredient1: String?,
    @Json(name = "strIngredient2")
    val strIngredient2: String?,
    @Json(name = "strIngredient3")
    val strIngredient3: String?,
    @Json(name = "strIngredient4")
    val strIngredient4: String?,
    @Json(name = "strIngredient5")
    val strIngredient5: String?,
    @Json(name = "strIngredient6")
    val strIngredient6: String?,
    @Json(name = "strIngredient7")
    val strIngredient7: String?,
    @Json(name = "strIngredient8")
    val strIngredient8: String?,
    @Json(name = "strIngredient9")
    val strIngredient9: String?,
    @Json(name = "strIngredient10")
    val strIngredient10: String?,
    @Json(name = "strIngredient11")
    val strIngredient11: String?,
    @Json(name = "strIngredient12")
    val strIngredient12: String?,
    @Json(name = "strIngredient13")
    val strIngredient13: String?,
    @Json(name = "strIngredient14")
    val strIngredient14: String?,
    @Json(name = "strIngredient15")
    val strIngredient15: String?,
    @Json(name = "strMeasure1")
    val strMeasure1: String?,
    @Json(name = "strMeasure2")
    val strMeasure2: String?,
    @Json(name = "strMeasure3")
    val strMeasure3: String?,
    @Json(name = "strMeasure4")
    val strMeasure4: String?,
    @Json(name = "strMeasure5")
    val strMeasure5: String?,
    @Json(name = "strMeasure6")
    val strMeasure6: String?,
    @Json(name = "strMeasure7")
    val strMeasure7: String?,
    @Json(name = "strMeasure8")
    val strMeasure8: String?,
    @Json(name = "strMeasure9")
    val strMeasure9: String?,
    @Json(name = "strMeasure10")
    val strMeasure10: String?,
    @Json(name = "strMeasure11")
    val strMeasure11: String?,
    @Json(name = "strMeasure12")
    val strMeasure12: String?,
    @Json(name = "strMeasure13")
    val strMeasure13: String?,
    @Json(name = "strMeasure14")
    val strMeasure14: String?,
    @Json(name = "strMeasure15")
    val strMeasure15: String?,
    @Json(name = "strImageSource")
    val strImageSource: String?,
    @Json(name = "strImageAttribution")
    val strImageAttribution: String?,
    @Json(name = "strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,
    @Json(name = "dateModified")
    val dateModified: String?
)

fun DrinkDto.toDrinkMapped() = DrinkMapped(
    idDrink = idDrink,
    strDrink = strDrink,
    strCategory = strCategory,
    strInstructions = strInstructions,
    strDrinkThumb = strDrinkThumb,
    strIngredient1 = if (strIngredient1 != null) "$strIngredient1 $strMeasure1" else "",
    strIngredient2 = if (strIngredient2 != null) "$strIngredient2 $strMeasure2" else "",
    strIngredient3 = if (strIngredient3 != null) "$strIngredient3 $strMeasure3" else "",
    strIngredient4 = if (strIngredient4 != null) "$strIngredient4 $strMeasure4" else "",
    strIngredient5 = if (strIngredient5 != null) "$strIngredient5 $strMeasure5" else "",
    strIngredient6 = if (strIngredient6 != null) "$strIngredient6 $strMeasure6" else "",
    strIngredient7 = if (strIngredient7 != null) "$strIngredient7 $strMeasure7" else "",
    strIngredient8 = if (strIngredient8 != null) "$strIngredient8 $strMeasure8" else "",
    strIngredient9 = if (strIngredient9 != null) "$strIngredient9 $strMeasure9" else "",
    strIngredient10 = if (strIngredient10 != null) "$strIngredient10 $strMeasure10" else "",
    strIngredient11 = if (strIngredient11 != null) "$strIngredient11 $strMeasure11" else "",
    strIngredient12 = if (strIngredient12 != null) "$strIngredient12 $strMeasure12" else "",
    strIngredient13 = if (strIngredient13 != null) "$strIngredient13 $strMeasure13" else "",
    strIngredient14 = if (strIngredient14 != null) "$strIngredient14 $strMeasure14" else "",
    strIngredient15 = if (strIngredient15 != null) "$strIngredient15 $strMeasure15" else "",
)