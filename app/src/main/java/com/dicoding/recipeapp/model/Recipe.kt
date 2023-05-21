package com.dicoding.recipeapp.model

data class Recipe(
    val id: String,
    val title: String,
    val photoUrl: String,
    val creator: String,
    val description: String,
    val ingredients: String,
    val stepByStep: String
)
