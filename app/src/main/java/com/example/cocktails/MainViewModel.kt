package com.example.cocktails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.remote.ApiService
import com.example.cocktails.data.remote.DrinkDto
import com.example.cocktails.data.remote.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
): ViewModel() {

    fun getAll(){
        viewModelScope.launch {
            cocktailRepository.getAllCocktailRemote()
        }
    }
}