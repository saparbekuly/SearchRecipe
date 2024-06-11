package com.application.searchrecipe.data.remote

data class RecipeResponse(
    val hits: List<Hit>
)

data class Hit(
    val recipe: Recipe
)

data class Recipe(
    val label: String,
    val image: String,
    val url: String,
    val ingredientLines: List<String>
)
