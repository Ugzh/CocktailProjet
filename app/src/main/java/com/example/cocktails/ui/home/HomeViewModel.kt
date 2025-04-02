package com.example.cocktails.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.remote.DrinkCategory
import com.example.cocktails.data.remote.DrinkDto
import com.example.cocktails.data.remote.toDrinkMapped
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.utils.CocktailMerged
import com.example.cocktails.utils.DrinkMapped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
): ViewModel() {

    private var _listMergedLiveData = MutableLiveData<List<CocktailMerged>>()
    val listMergedLiveData: LiveData<List<CocktailMerged>>
        get() = _listMergedLiveData

    private var _userMessageLiveData = MutableLiveData<Int>()
    val userMessageLiveData: LiveData<Int>
        get() =  _userMessageLiveData

    private var _userNavigationLiveData = MutableLiveData<NavDirections?>()
    val userNavigationLiveData: LiveData<NavDirections?>
        get() = _userNavigationLiveData

    private var listAPI = mutableListOf<DrinkCategory>()


    init {
        viewModelScope.launch {
            _listMergedLiveData.value = emptyList()
            listAPI.addAll(cocktailRepository.getAllCocktailRemote().first)
            val listLocal = cocktailRepository.getAllCocktailLocal()
            _userMessageLiveData.value = cocktailRepository.getAllCocktailRemote().second
            createMergedList(listAPI, listLocal)
        }
    }

    fun openEditOrDetailFragment(cocktail: CocktailMerged){
        if(cocktail.uuid != null)
            _userNavigationLiveData.value =
                HomeFragmentDirections.
                actionHomeFragmentToEditFragment(cocktail)
        else
            _userNavigationLiveData.value =
                HomeFragmentDirections.
                actionHomeFragmentToDetailFragment(cocktail.id!!)
        resetNav()
    }

    fun openNewArticleFragment(){
        _userNavigationLiveData.value =
            HomeFragmentDirections.
            actionHomeFragmentToCreateFragment()
        resetNav()
    }

    private fun resetNav(){
        _userMessageLiveData.value = 0
        _userNavigationLiveData.value =
            null
    }

    private fun createMergedList(listAPI: List<DrinkCategory>, listRoom: List<Cocktail>){
        _listMergedLiveData.value = emptyList()
        listAPI.forEach {
            /*var ing = ""
            val mappedDrink = it.toDrinkMapped()
            for (prop in DrinkMapped::class.memberProperties) {
                if(prop.name.contains("strIngredient") && prop.get(mappedDrink) != ""){
                    ing += "${prop.get(mappedDrink)}\n"
                }
            }*/

            _listMergedLiveData.value = (_listMergedLiveData.value ?: emptyList()) + CocktailMerged(
                id = it.idDrink.toInt(),
                uuid = null,
                title = it.strDrink,
                imageUrl = it.strDrinkThumb,
                ingredients = null,
                instructions = null
            )
        }

        listRoom.forEach {
            with(it){
                _listMergedLiveData.value = (_listMergedLiveData.value ?: emptyList()) + CocktailMerged(
                    id = null,
                    uuid = uuid,
                    title = name,
                    imageUrl = url,
                    ingredients = ingredients,
                    instructions = instructions
                )
            }
        }
        _listMergedLiveData.value = _listMergedLiveData.value!!.sortedBy { it.title }
    }


    fun getNewLocalList(){
        viewModelScope.launch {
            val listLocal = cocktailRepository.getAllCocktailLocal()
            createMergedList(listAPI, listLocal)
        }
    }
}