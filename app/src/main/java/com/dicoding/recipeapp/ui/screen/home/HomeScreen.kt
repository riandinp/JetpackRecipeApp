@file:OptIn(ExperimentalFoundationApi::class)

package com.dicoding.recipeapp.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.recipeapp.R
import com.dicoding.recipeapp.model.FakeRecipeDataSource
import com.dicoding.recipeapp.model.FavoriteRecipe
import com.dicoding.recipeapp.ui.ViewModelFactory
import com.dicoding.recipeapp.ui.common.UiState
import com.dicoding.recipeapp.ui.components.CardRecipe
import com.dicoding.recipeapp.ui.components.SearchBar
import com.dicoding.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory()),
    navigateToDetail: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllRecipe()
            }
            is UiState.Success -> {
                HomeContent(
                    listRecipe = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier,
                    viewModel = viewModel,
                )
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, stringResource(R.string.error_message, uiState.errorMessage), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun HomeContent(
    listRecipe: List<FavoriteRecipe>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    Box {
        Column {
            val query by viewModel.query
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                items(listRecipe) {
                    CardRecipe(
                        photoUrl = it.recipe.photoUrl,
                        title = it.recipe.title,
                        creator = it.recipe.creator,
                        modifier = Modifier
                            .clickable { navigateToDetail(it.recipe.id) }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreenPreview() {
    RecipeAppTheme {
        HomeContent(
            listRecipe = listOf(
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[0], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[1], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[2], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[3], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[3], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[3], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[3], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[3], false),
                FavoriteRecipe(FakeRecipeDataSource.dummyRecipe[3], false),
            ),
            navigateToDetail = {},
            viewModel = viewModel(factory = ViewModelFactory())
        )
    }
}