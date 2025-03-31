package com.example.cocktails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.remote.ApiService
import com.example.cocktails.data.remote.DrinkDto
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.utils.CocktailMerged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
): ViewModel() {

    private var _listMerged = MutableLiveData<List<CocktailMerged>>()
    var listMerged: LiveData<List<CocktailMerged>> = _listMerged

    fun getAll(){
        viewModelScope.launch {
            var mergedList = mutableListOf<CocktailMerged>()
            val listAPI = cocktailRepository.getAllCocktailRemote().first
            cocktailRepository
                .createLocalCocktail(
                    Cocktail(
                        UUID.randomUUID(),
                        "Vodka 2",
                        "x",
                        "nothing",
                        "nothing ing")
                )

            val listLocal = cocktailRepository.getAllCocktailLocal()


             listAPI.forEach {
                 with(it){
                     mergedList.add(CocktailMerged(
                         id = idDrink.toInt(),
                         uuid = null,
                         title = strDrink!!,
                         imageUrl = strDrinkThumb!!,
                         ingredients = strIngredient1!!,
                         instructions = strInstructions!!
                     ))
                 }
             }

            listLocal.forEach {
                with(it){
                    mergedList.add(
                        CocktailMerged(
                            id = null,
                            uuid = uuid,
                            title = name,
                            imageUrl = url,
                            ingredients = ingredients,
                            instructions = instructions
                        ) )
                }
            }
            _listMerged.value = mergedList
        }
    }
}