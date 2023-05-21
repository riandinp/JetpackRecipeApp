@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.dicoding.recipeapp.ui.screen.favorite

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.recipeapp.R
import com.dicoding.recipeapp.model.FavoriteRecipe
import com.dicoding.recipeapp.ui.ViewModelFactory
import com.dicoding.recipeapp.ui.common.UiState
import com.dicoding.recipeapp.ui.components.CardRecipe
import com.dicoding.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(factory = ViewModelFactory()),
    navigateToDetail: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.menu_favorite),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.surface
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(it)
        ) {
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getAllFavoriteRecipe()
                    }
                    is UiState.Success -> {
                        val favoriteRecipe = uiState.data
                        if (favoriteRecipe.isNotEmpty()) {
                            FavoriteContent(
                                listRecipe = uiState.data,
                                navigateToDetail = navigateToDetail,
                                modifier = modifier,
                            )
                        } else {
                            Box(
                                modifier = modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.no_recipe_favorite),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        Toast.makeText(LocalContext.current, stringResource(R.string.error_message, uiState.errorMessage), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listRecipe: List<FavoriteRecipe>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column {
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
                        modifier = modifier
                            .clickable { navigateToDetail(it.recipe.id) }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun FavoriteScreenPreview() {
    RecipeAppTheme {
        FavoriteScreen {}
    }
}