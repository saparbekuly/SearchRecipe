package com.application.searchrecipe.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.searchrecipe.data.repository.RecipeRepository
import com.application.searchrecipe.data.local.RecipeEntity
import com.application.searchrecipe.data.remote.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> get() = _recipes

    private val _savedRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val savedRecipes: StateFlow<List<RecipeEntity>> get() = _savedRecipes

    init {
        initializeRecipesWithSaved()
    }

    private fun initializeRecipesWithSaved() {
        viewModelScope.launch {
            try {
                _savedRecipes.value = repository.getAllSavedRecipes()
                _recipes.value = _savedRecipes.value.map { recipeEntity ->
                    Recipe(
                        label = recipeEntity.label,
                        image = recipeEntity.image,
                        url = recipeEntity.url,
                        ingredientLines = recipeEntity.ingredientLines
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in initializing recipes with saved recipes", e)
            }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                Log.e(TAG, "Successfully fetching recipes")
                val result = repository.searchRecipes(query)
                _recipes.value = result
            } catch (e: Exception) {
                Log.e(TAG, "Error in fetching recipes")
                _recipes.value = emptyList()
            }
        }
    }

    fun insertRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.insertRecipe(recipe)
            loadSavedRecipes()
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
            loadSavedRecipes()
        }
    }

    fun loadSavedRecipes() {
        viewModelScope.launch {
            _savedRecipes.value = repository.getAllSavedRecipes()
        }
    }
}

