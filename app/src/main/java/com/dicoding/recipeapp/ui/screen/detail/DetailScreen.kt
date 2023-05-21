@file:OptIn(ExperimentalMaterial3Api::class)

package com.dicoding.recipeapp.ui.screen.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.recipeapp.R
import com.dicoding.recipeapp.model.FavoriteRecipe
import com.dicoding.recipeapp.ui.ViewModelFactory
import com.dicoding.recipeapp.ui.common.UiState
import com.dicoding.recipeapp.ui.components.showToast

@Composable
fun DetailScreen(
    recipeId: String,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory()),
    context: Context = LocalContext.current,
    onNavigateBack: () -> Unit
) {
    val favoriteText = stringResource(R.string.menu_detail)
    val addFavoriteText = stringResource(R.string.favorite_added)
    val deleteFavoriteText = stringResource(R.string.favorite_deleted)

    var mCurrentToast: Toast? = null // Untuk Handling Toast, ketika dijalankan terus dalam waktu yang cepat
    var titleBar by remember { mutableStateOf(favoriteText) }
    var checked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = titleBar,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
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
                ),
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.surface,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                actions = {
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = {
                            mCurrentToast =
                                if (checked) showToast(context, deleteFavoriteText, currentToast = mCurrentToast)
                                else showToast(context, addFavoriteText, currentToast = mCurrentToast)
                            mCurrentToast?.show()
                            checked = !checked
                            viewModel.updateFavoriteRecipe(recipeId, checked)
                        },
                        modifier = modifier.padding(10.dp)
                    ) {
                        val transition =
                            updateTransition(checked, label = stringResource(R.string.is_favorite))

                        val tint by transition.animateColor(label = "iconColor") { isChecked ->
                            if (isChecked) Color.Red else MaterialTheme.colorScheme.surface
                        }

                        Icon(
                            imageVector = if (checked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(R.string.icon),
                            tint = tint,
                            modifier = modifier.size(24.dp)
                        )
                    }

                }
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getRecipeById(recipeId)
                    }

                    is UiState.Success -> {
                        titleBar = uiState.data.recipe.title
                        checked = uiState.data.isFavorite
                        DetailContent(uiState.data)
                    }

                    is UiState.Error -> {
                        mCurrentToast = showToast(context, stringResource(R.string.error_message, uiState.errorMessage), Toast.LENGTH_LONG, mCurrentToast)
                        mCurrentToast?.show()
                    }
                }
            }
        }
    }

}


@Composable
fun DetailContent(
    recipe: FavoriteRecipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        AsyncImage(
            model = recipe.recipe.photoUrl,
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(
                R.string.desc_image, recipe.recipe.title
            ),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp)
                .shadow(elevation = 4.dp)
        )
        Text(
            text = recipe.recipe.title,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )

        Text(
            text = stringResource(R.string.create_by, recipe.recipe.creator),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.secondary
            )
        )

        CreateSubContent(stringResource(R.string.description), recipe.recipe.description)
        CreateSubContent(stringResource(R.string.ingredients), recipe.recipe.ingredients)
        CreateSubContent(stringResource(R.string.stepbystep), recipe.recipe.stepByStep)
    }
}

@Composable
fun CreateSubContent(subTitle: String, content: String, modifier: Modifier = Modifier) {
    Text(
        text = subTitle,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )
    )

    Text(
        text = content,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .padding(horizontal = 16.dp),
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Justify
        )
    )
}