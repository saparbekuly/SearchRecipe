package com.application.searchrecipe.data.repository

import com.application.searchrecipe.data.local.RecipeDao
import com.application.searchrecipe.data.local.RecipeEntity
import com.application.searchrecipe.data.remote.Recipe
import com.application.searchrecipe.data.remote.RecipeAPI
import com.application.searchrecipe.utils.Constants.APP_ID
import com.application.searchrecipe.utils.Constants.APP_KEY

class RecipeRepository(private val api: RecipeAPI, private val dao: RecipeDao) {
    suspend fun searchRecipes(query: String): List<Recipe> {
        val response = api.searchRecipes(query, APP_ID, APP_KEY)
        return response.hits.map { it.recipe }
    }

    suspend fun insertRecipe(recipe: RecipeEntity) {
        dao.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) {
        dao.deleteRecipe(recipe)
    }

    suspend fun getAllSavedRecipes(): List<RecipeEntity> {
        return dao.getAllRecipes()
    }
}