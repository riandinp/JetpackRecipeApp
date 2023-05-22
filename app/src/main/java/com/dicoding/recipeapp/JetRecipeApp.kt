@file:OptIn(ExperimentalMaterial3Api::class)

package com.dicoding.recipeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicoding.recipeapp.ui.components.BottomBar
import com.dicoding.recipeapp.ui.navigation.Screen
import com.dicoding.recipeapp.ui.navigation.SetupNavGraph
import com.dicoding.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun JetRecipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailRecipe.route && currentRoute != Screen.ProfileWeb.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        SetupNavGraph(modifier = modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
@Preview(showBackground = true)
fun JetRecipeAppPreview() {
    RecipeAppTheme {
        JetRecipeApp()
    }
}