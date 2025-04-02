package com.example.cocktails.data.repositories

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.local.dao.CocktailDao
import com.example.cocktails.data.remote.ApiService
import com.example.cocktails.data.remote.DrinkCategory
import com.example.cocktails.data.remote.DrinkDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor(
    private val apiService: ApiService,
    private val cocktailDao: CocktailDao
){
    /*suspend fun getAllCocktailRemote(): Pair<List<DrinkDto>, Int?>{
        val list = mutableListOf<DrinkDto>()
        var error = 0
        for( ch in 'a'..'z'){
            val response = withContext(Dispatchers.IO){
                apiService.getAllDrinks(ch)
            }
            val body = response?.body()

            when{
                response == null -> error = R.string.error_db
                body == null ->  error = R.string.error_db
                response.isSuccessful -> {
                    body.drinks?.let {
                        list.addAll(it)
                    }
                    body.drinksError?.let {
                        error = R.string.error_db
                    }
                }
            }
        }
        for(ch in 1..9){
            val response = withContext(Dispatchers.IO){
                apiService.getAllDrinks(ch.digitToChar())
            }
            val body = response?.body()

            when{
                response == null -> error = R.string.error_db
                body == null ->  error = R.string.letter_or_number_without_data
                response.isSuccessful -> {
                    body.drinks?.let {
                        list.addAll(it)
                    }
                    body.drinksError?.let {
                        error = R.string.letter_or_number_without_data
                    }
                }
            }
        }

        return Pair(
            list.filter { it.strCategory == "Cocktail" },
            if(error != 0) error else null)
    }*/
    suspend fun getAllCocktailRemote(): Pair<List<DrinkCategory>, Int?>{
        val list = mutableListOf<DrinkCategory>()
        var error = 0
        val response = withContext(Dispatchers.IO){
            apiService.getAllDrinks()
        }
        val body = response?.body()

        when{
            response == null -> error = R.string.error_db
            body == null ->  error = R.string.error_db
            response.isSuccessful -> {
                body.drinks.let {
                    list.addAll(it)
                }
            }
        }

        return Pair(
            list,
            if(error != 0) error else 0)
    }

    suspend fun getCocktailById(id: String): Pair<DrinkDto, Int>{
        val list = mutableListOf<DrinkDto>()
        var error = 0

        val response = withContext(Dispatchers.IO){
            apiService.getDrinkById(id)
        }
        val body = response?.body()

        when{
            response == null -> error = R.string.error_db
            body == null ->  error = R.string.error_db
            response.isSuccessful -> {
                body.drinks?.let {
                    list.addAll(it)
                }
                body.drinksError?.let {
                    error = R.string.error_db
                }
            }
        }
        return Pair(list.get(0),error)
    }

    suspend fun getAllCocktailLocal(): List<Cocktail>{
        return cocktailDao.getAllCocktail()
    }

    suspend fun createLocalCocktail(cocktail: Cocktail): Pair<Boolean, Int>{
        val ingredient = cocktail.ingredients.trim()
        val name = cocktail.name.trim()
        val instructions = cocktail.instructions.trim()
        val url = cocktail.url.trim()
        var isAdded = false
        var messageOnFailure = 0

        if (
            ingredient.isNotEmpty() &&
            name.isNotEmpty() &&
            instructions.isNotEmpty()
            ){
            withContext(Dispatchers.IO){
                cocktailDao.addCocktail(Cocktail(
                    cocktail.uuid,
                    name,
                    url,
                    instructions,
                    ingredient,

                ))
            }

            isAdded = true
        } else {
            messageOnFailure = R.string.fill_field
        }

        return Pair(isAdded, messageOnFailure)
    }

    suspend fun deleteCocktail(cocktail: Cocktail): Pair<Boolean, Int>{
        val ingredient = cocktail.ingredients.trim()
        val name = cocktail.name.trim()
        val instructions = cocktail.instructions.trim()
        val url = cocktail.url.trim()
        var isDeleted = false
        var messageOnFailure = 0

        if (
            ingredient.isNotEmpty() &&
            name.isNotEmpty() &&
            instructions.isNotEmpty()
        ){
            withContext(Dispatchers.IO){
                cocktailDao.deleteCocktail(Cocktail(
                    cocktail.uuid,
                    name,
                    url,
                    instructions,
                    ingredient,
                ))
            }

            isDeleted = true
        } else {
            messageOnFailure = R.string.fill_field
        }

        return Pair(isDeleted, messageOnFailure)
    }

    suspend fun editCocktail(cocktail: Cocktail): Pair<Boolean, Int>{
        val ingredient = cocktail.ingredients.trim()
        val name = cocktail.name.trim()
        val instructions = cocktail.instructions.trim()
        val url = cocktail.url.trim()
        var isUpdated = false
        var messageOnFailure = 0

        if (
            ingredient.isNotEmpty() &&
            name.isNotEmpty() &&
            instructions.isNotEmpty()
        ){
            withContext(Dispatchers.IO){
                cocktailDao.updateCocktail(Cocktail(
                    cocktail.uuid,
                    name,
                    url,
                    instructions,
                    ingredient,
                ))
            }

            isUpdated = true
        } else {
            messageOnFailure = R.string.fill_field
        }

        return Pair(isUpdated, messageOnFailure)
    }
}