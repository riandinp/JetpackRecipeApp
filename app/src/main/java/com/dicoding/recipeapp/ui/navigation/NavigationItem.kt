package com.dicoding.recipeapp.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val screen: Screen
)