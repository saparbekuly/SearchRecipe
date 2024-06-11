package com.application.searchrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.application.searchrecipe.ui.RecipeViewModel
import com.application.searchrecipe.utils.SearchRecipe
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: RecipeViewModel = getViewModel()
            val navController = rememberNavController()
            SearchRecipe(navController, viewModel)
        }
    }
}



