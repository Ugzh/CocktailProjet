package com.example.cocktails.data.remote.repositories

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.remote.ApiService
import com.example.cocktails.data.remote.DrinkDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getAllCocktailRemote(): List<DrinkDto>{
        val list = mutableListOf<DrinkDto>()
        val error = ""
        for( ch in 'a'..'z'){
            val response = withContext(Dispatchers.IO){
                apiService.getAllDrinks(ch)
            }
            val body = response?.body()

            when{
               response == null -> print("")
                body == null -> print("")
                response.isSuccessful -> {
                    body.drinks?.let {
                        list.addAll(it)
                    }
                }
            }
        }

        return list.filter {
            it.strCategory == "Cocktail"
        }
    }

}