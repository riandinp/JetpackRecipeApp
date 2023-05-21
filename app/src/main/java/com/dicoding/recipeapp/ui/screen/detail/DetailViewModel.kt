package com.dicoding.recipeapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recipeapp.model.FavoriteRecipe
import com.dicoding.recipeapp.repository.RecipeRepository
import com.dicoding.recipeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<FavoriteRecipe>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteRecipe>>
        get() = _uiState

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            repository.getRecipeById(recipeId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { detailRecipe ->
                    _uiState.value = UiState.Success(detailRecipe)
                }
        }
    }

    fun updateFavoriteRecipe(recipeId: String, newState: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteRecipe(recipeId, newState)
        }
    }


}