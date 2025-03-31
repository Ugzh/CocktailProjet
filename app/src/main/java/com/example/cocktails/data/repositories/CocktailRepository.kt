package com.example.cocktails.data.repositories

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.local.dao.CocktailDao
import com.example.cocktails.data.remote.ApiService
import com.example.cocktails.data.remote.DrinkDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor(
    private val apiService: ApiService,
    private val cocktailDao: CocktailDao
){
    suspend fun getAllCocktailRemote(): Pair<List<DrinkDto>, Int>{
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

        return Pair(list.filter {
            it.strCategory == "Cocktail"
        }, error)
    }

    suspend fun getCocktailById(id: String): Pair<List<DrinkDto>, Int>{
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
        return Pair(list,error)
    }

    suspend fun getAllCocktailLocal(): List<Cocktail>{
        return cocktailDao.getAllCocktail()
    }

    suspend fun createLocalCocktail(cocktail: Cocktail): String{
        withContext(Dispatchers.IO){
            cocktailDao.addCocktail(cocktail)
        }
        return "ok"
    }
}