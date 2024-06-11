package com.application.searchrecipe.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI {
    @GET("search")
    suspend fun searchRecipes(
        @Query("q") query: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String
    ): RecipeResponse
}