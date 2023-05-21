package com.dicoding.recipeapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recipeapp.model.FavoriteRecipe
import com.dicoding.recipeapp.repository.RecipeRepository
import com.dicoding.recipeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RecipeRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<FavoriteRecipe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteRecipe>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun search(newQuery: String) {
        _query.value = newQuery
        _uiState.value = UiState.Success(repository.findRecipe(_query.value))
    }


    fun getAllRecipe() {
        viewModelScope.launch {
            repository.getAllRecipe()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { listRecipe ->
                    _uiState.value = UiState.Success(listRecipe)
                }
        }
    }

}