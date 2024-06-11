package com.application.searchrecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> = value.split(",")

    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")
}