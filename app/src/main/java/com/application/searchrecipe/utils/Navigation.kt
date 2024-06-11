package com.application.searchrecipe.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.application.searchrecipe.data.remote.Recipe
import com.application.searchrecipe.ui.RecipeViewModel
import com.application.searchrecipe.ui.screens.RecipeScreen
import com.application.searchrecipe.ui.screens.SavedRecipesScreen
import com.application.searchrecipe.ui.screens.SearchRecipesScreen

@Composable
fun SearchRecipe(navController: NavHostController, viewModel: RecipeViewModel) {
    NavHost(navController = navController, startDestination = "searchRecipes") {
        composable("searchRecipes") { SearchRecipesScreen(navController, viewModel) }
        composable("savedRecipes") { SavedRecipesScreen(navController, viewModel) }
        composable(
            "recipe/{label}/{image}/{url}/{ingredientLines}/{isSaved}",
            arguments = listOf(
                navArgument("label") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType },
                navArgument("url") { type = NavType.StringType },
                navArgument("ingredientLines") { type = NavType.StringType },
                navArgument("isSaved") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val label = backStackEntry.arguments?.getString("label") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: ""
            val url = backStackEntry.arguments?.getString("url") ?: ""
            val ingredientLines = backStackEntry.arguments?.getString("ingredientLines")?.split(",") ?: emptyList()
            val isSaved = backStackEntry.arguments?.getBoolean("isSaved") ?: false

            val recipe = Recipe(label, image, url, ingredientLines)
            RecipeScreen(navController, viewModel, recipe, isSaved)
        }
    }
}