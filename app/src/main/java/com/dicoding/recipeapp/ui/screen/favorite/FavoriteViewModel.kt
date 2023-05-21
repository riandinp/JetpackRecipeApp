package com.dicoding.recipeapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recipeapp.model.FavoriteRecipe
import com.dicoding.recipeapp.repository.RecipeRepository
import com.dicoding.recipeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: RecipeRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteRecipe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteRecipe>>>
        get() = _uiState

    fun getAllFavoriteRecipe() {
        viewModelScope.launch {
            repository.getAllFavoriteRecipe()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { listRecipe ->
                    _uiState.value = UiState.Success(listRecipe)
                }
        }
    }
}