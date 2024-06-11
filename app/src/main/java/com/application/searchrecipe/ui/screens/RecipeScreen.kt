package com.application.searchrecipe.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.application.searchrecipe.R
import com.application.searchrecipe.data.local.RecipeEntity
import com.application.searchrecipe.data.remote.Recipe
import com.application.searchrecipe.ui.RecipeViewModel
import com.application.searchrecipe.utils.Inter

@Composable
fun RecipeScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel,
    recipe: Recipe,
    isSaved: Boolean
) {
    var isLiked by remember { mutableStateOf(isSaved) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    if (isSaved) navController.navigate("savedRecipes")
                    else navController.navigate("searchRecipes")
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Go Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                BasicText(
                    text = recipe.label,
                    style = TextStyle(fontSize = 24.sp, fontFamily = Inter),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    isLiked = !isLiked
                    val recipeEntity = RecipeEntity(
                        label = recipe.label,
                        image = recipe.image,
                        url = recipe.url,
                        ingredientLines = recipe.ingredientLines
                    )
                    if (isLiked) {
                        viewModel.insertRecipe(recipeEntity)
                    } else {
                        viewModel.deleteRecipe(recipeEntity)
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = if (isLiked) R.drawable.liked else R.drawable.unliked),
                    contentDescription = "Like Button",
                    modifier = Modifier.size(30.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = rememberAsyncImagePainter(recipe.image),
            contentDescription = recipe.label,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Ingredients",
            style = TextStyle(fontSize = 20.sp, fontFamily = Inter),
        )

        Spacer(modifier = Modifier.height(8.dp))

        recipe.ingredientLines.forEach { ingredient ->
            Text(
                text = "- $ingredient",
                style = TextStyle(fontSize = 16.sp, fontFamily = Inter),
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.url))
                context.startActivity(intent)
            }
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "View for more details",
                fontSize = 16.sp,
                fontFamily = Inter,
                color = Color(0xFF0161ff)
            )
        }
    }
}
