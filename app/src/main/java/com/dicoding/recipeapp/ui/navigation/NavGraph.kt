package com.dicoding.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dicoding.recipeapp.ui.screen.detail.DetailScreen
import com.dicoding.recipeapp.ui.screen.favorite.FavoriteScreen
import com.dicoding.recipeapp.ui.screen.home.HomeScreen
import com.dicoding.recipeapp.ui.screen.profile.ProfileScreen
import com.dicoding.recipeapp.ui.screen.profile.ProfileWebScreen

@Composable
fun SetupNavGraph(
    modifier: Modifier,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            HomeScreen {
                navController.navigate(Screen.DetailRecipe.createRoute(it))
            }
        }
        composable(
            route = Screen.DetailRecipe.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("recipeId") ?:"0"
            DetailScreen(
                recipeId = id,
                onNavigateBack = {navController.navigateUp()}
            )
        }
        composable(route = Screen.Favorite.route) {
            FavoriteScreen { navController.navigate(Screen.DetailRecipe.createRoute(it)) }
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen {
                navController.navigate(Screen.ProfileWeb.createRoute(it))
            }
        }
        composable(
            route = Screen.ProfileWeb.route,
            arguments = listOf(navArgument("userName") {type = NavType.StringType})
        ) {
            val username = it.arguments?.getString("userName") ?:""
            ProfileWebScreen(username) {
                navController.navigateUp()
            }
        }

    }
}