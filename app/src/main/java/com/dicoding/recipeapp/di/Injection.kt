package com.dicoding.recipeapp.di

import com.dicoding.recipeapp.repository.RecipeRepository

object Injection {
    fun provideRepository(): RecipeRepository {
        return RecipeRepository.getInstance()
    }
}