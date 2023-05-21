package com.dicoding.recipeapp.repository

import com.dicoding.recipeapp.model.FakeRecipeDataSource
import com.dicoding.recipeapp.model.FavoriteRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RecipeRepository {

    private val listRecipe = mutableListOf<FavoriteRecipe>()

    init {
        if (listRecipe.isEmpty()) {
            FakeRecipeDataSource.dummyRecipe.forEach {
                listRecipe.add(FavoriteRecipe(it, false))
            }
        }
    }


    fun getAllRecipe(): Flow<List<FavoriteRecipe>> {
        return flowOf(listRecipe)
    }

    fun getRecipeById(recipeId: String): Flow<FavoriteRecipe> {
        return flowOf(
            listRecipe.first {
                it.recipe.id == recipeId
            }
        )
    }

    fun findRecipe(query: String): List<FavoriteRecipe> {
        return listRecipe.filter {
            it.recipe.title.contains(query, ignoreCase = true)
        }
    }

    fun getAllFavoriteRecipe(): Flow<List<FavoriteRecipe>> {
        return flowOf(listRecipe).map { favoriteRecipe ->
            favoriteRecipe.filter { it.isFavorite }
        }
    }

    fun updateFavoriteRecipe(recipeId: String, newState: Boolean): Flow<Boolean> {
        val index = listRecipe.indexOfFirst { it.recipe.id == recipeId }
        val result = if (index >= 0) {
            val recipe = listRecipe[index]
            listRecipe[index] = recipe.copy(recipe = recipe.recipe, isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }


    companion object {
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(): RecipeRepository =
            instance ?: synchronized(this) {
                RecipeRepository().apply {
                    instance = this
                }
            }
    }
}