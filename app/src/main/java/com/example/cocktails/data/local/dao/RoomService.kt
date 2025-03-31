package com.example.cocktails.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cocktails.data.local.Cocktail

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCocktail(cocktail: Cocktail)
    @Query("SELECT *  FROM cocktail")
    fun getAllCocktail(): List<Cocktail>?

    @Query("SELECT * FROM cocktail WHERE uuid = :cocktailId")
    suspend fun getCocktailById(cocktailId: Int): Cocktail?

    @Update
    suspend fun updateCocktail(cocktail: Cocktail)

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)
}
