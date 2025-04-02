package com.example.cocktails.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.utils.CocktailMerged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private var _userMessageLiveData = MutableLiveData<Int>()
    val userMessageLiveData: LiveData<Int>
        get() =  _userMessageLiveData

    private var _isDeletedOrUpdated = MutableLiveData<Boolean>()
    val isDeletedOrUpdated: LiveData<Boolean> get() = _isDeletedOrUpdated

    fun deleteCocktail(cocktail: CocktailMerged){
        viewModelScope.launch {
            val deleteCocktail = cocktailRepository.deleteCocktail(
                Cocktail(
                cocktail.uuid!!,
                cocktail.title,
                cocktail.imageUrl,
                cocktail.instructions!!,
                cocktail.ingredients!!
            )
            )
            _userMessageLiveData.value = deleteCocktail.second

            _isDeletedOrUpdated.value = deleteCocktail.first
        }
    }

    fun updateCocktail(cocktail: CocktailMerged){
        viewModelScope.launch {
            val deleteCocktail = cocktailRepository.editCocktail(
                Cocktail(
                cocktail.uuid!!,
                cocktail.title,
                cocktail.imageUrl,
                cocktail.instructions!!,
                cocktail.ingredients!!
            )
            )
            val message = deleteCocktail.second
            _userMessageLiveData.value = if(message != 0) message else 0

            _isDeletedOrUpdated.value = deleteCocktail.first
        }
    }


}