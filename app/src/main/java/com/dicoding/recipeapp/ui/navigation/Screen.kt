package com.dicoding.recipeapp.ui.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Favorite : Screen("favorite")

    object Profile : Screen("profile")

    object DetailRecipe : Screen("detail/{recipeId}") {
        fun createRoute(recipeId: String) = "detail/$recipeId"
    }
}
