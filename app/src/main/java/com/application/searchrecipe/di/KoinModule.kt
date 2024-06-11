package com.application.searchrecipe.di

import androidx.room.Room
import com.application.searchrecipe.data.repository.RecipeRepository
import com.application.searchrecipe.data.local.RecipeDatabase
import com.application.searchrecipe.data.remote.RecipeAPI
import com.application.searchrecipe.ui.RecipeViewModel
import com.application.searchrecipe.utils.Constants.BASE_URL
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeAPI::class.java)
    }
    single {
        Room.databaseBuilder(androidContext(), RecipeDatabase::class.java, "recipe_database")
            .build()
    }
    single { get<RecipeDatabase>().recipeDao() }
    single { RecipeRepository(get(), get()) }
    viewModel { RecipeViewModel(get()) }
}
