package com.example.cocktails.ui.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel(){

    private var _userMessageMutableLiveData = MutableLiveData<Int>()
    val userMessageLiveData: LiveData<Int>
        get() =  _userMessageMutableLiveData

    private var _isAddedMutableLiveData = MutableLiveData<Boolean>()
    val isAddedLiveData: LiveData<Boolean> get() = _isAddedMutableLiveData

    fun addCocktail(cocktail: Cocktail){
        Log.d("test", "ici")
        viewModelScope.launch {
            val newCocktail = cocktailRepository.createLocalCocktail(cocktail)
            _userMessageMutableLiveData.value =
                if(newCocktail.second != 0) newCocktail.second else 0
            _isAddedMutableLiveData.value = newCocktail.first
        }
    }
}