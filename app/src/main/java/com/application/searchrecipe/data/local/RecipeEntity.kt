package com.application.searchrecipe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val label: String,
    val image: String,
    val url: String,
    val ingredientLines: List<String>
)