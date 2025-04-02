package com.example.cocktails.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.remote.toDrinkMapped
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.utils.CocktailMerged
import com.example.cocktails.utils.DrinkMapped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel(){
    private var _cocktailMutableLiveData = MutableLiveData<CocktailMerged>()
    val cocktailLiveData: LiveData<CocktailMerged> get() = _cocktailMutableLiveData

    private var _userMessageMutableLiveData = MutableLiveData<Int>()
    val userMessageLiveData: LiveData<Int>
        get() =  _userMessageMutableLiveData

    fun getCocktail(id: Int){
        viewModelScope.launch {
            val fetchCocktail = cocktailRepository.getCocktailById(id.toString())
            with(fetchCocktail.first.toDrinkMapped()){
                var ingredient = ""
                for (prop in DrinkMapped::class.memberProperties) {
                    if(prop.name.contains("strIngredient") && prop.get(this) != ""){
                        ingredient += "${prop.get(this)}\n"
                    }
                }
                _cocktailMutableLiveData.value = CocktailMerged(
                    id,
                    null,
                    strDrink ?: "",
                    strDrinkThumb ?: "",
                    ingredient,
                    strInstructions
                    )
            }
            _userMessageMutableLiveData.value = fetchCocktail.second
        }
    }
}