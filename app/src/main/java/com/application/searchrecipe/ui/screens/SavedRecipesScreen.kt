package com.application.searchrecipe.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.application.searchrecipe.R
import com.application.searchrecipe.data.remote.Recipe
import com.application.searchrecipe.ui.RecipeViewModel
import com.application.searchrecipe.ui.common.RecipeItem
import com.application.searchrecipe.utils.Inter

@Composable
fun SavedRecipesScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel
) {
    val savedRecipes by viewModel.savedRecipes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSavedRecipes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = {
                    navController.navigate("searchRecipes")
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Go Back",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopStart)
                )
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Favorites",
                fontFamily = Inter,
                fontSize = 24.sp
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
        ) {
            items(savedRecipes) { recipe ->
                RecipeItem(
                    recipe = Recipe(
                        label = recipe.label,
                        image = recipe.image,
                        url = recipe.url,
                        ingredientLines = recipe.ingredientLines
                    ),
                    onClick = {
                        val label = Uri.encode(recipe.label)
                        val image = Uri.encode(recipe.image)
                        val url = Uri.encode(recipe.url)
                        val ingredientLinesString =
                            Uri.encode(recipe.ingredientLines.joinToString(","))

                        navController.navigate("recipe/$label/$image/$url/$ingredientLinesString/true")
                    }
                )
            }
        }
    }
}