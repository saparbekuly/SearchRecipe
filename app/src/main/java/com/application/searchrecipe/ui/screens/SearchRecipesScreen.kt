package com.application.searchrecipe.ui.screens

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.application.searchrecipe.R
import com.application.searchrecipe.ui.RecipeViewModel
import com.application.searchrecipe.ui.common.RecipeItem
import com.application.searchrecipe.ui.theme.Outline
import com.application.searchrecipe.utils.Inter
import com.application.searchrecipe.utils.isInternetAvailable

@Composable
fun SearchRecipesScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel
) {
    val recipes by viewModel.recipes.collectAsState()
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val isOnline = remember { mutableStateOf(true) }
    var searchAttempted by remember { mutableStateOf(false) }

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
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Search",
                fontSize = 30.sp,
                fontFamily = Inter,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(50.dp),
                onClick = {
                    navController.navigate("savedRecipes")
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favorites),
                    contentDescription = "Favorites",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(35.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "What would you like to cook?",
                fontSize = 20.sp,
                fontFamily = Inter,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Enter the name of the dish",
                fontSize = 16.sp,
                fontFamily = Inter,
                color = Color.DarkGray
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Type the name of the dish...",
                    fontFamily = Inter,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = Inter),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Outline,
                focusedBorderColor = Outline,
                unfocusedBorderColor = Color.Gray,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    isOnline.value = isInternetAvailable(context)
                    viewModel.searchRecipes(query)
                    searchAttempted = true
                    keyboardController?.hide()
                }
            )
        )


        if (isOnline.value) {
            if (searchAttempted && recipes.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No recipes found",
                        fontSize = 18.sp,
                        fontFamily = Inter,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "How about trying something else?",
                        fontSize = 15.sp,
                        fontFamily = Inter,
                        color = Color.Gray
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                ) {
                    items(recipes) { recipe ->
                        RecipeItem(recipe) {
                            val label = Uri.encode(recipe.label)
                            val image = Uri.encode(recipe.image)
                            val url = Uri.encode(recipe.url)
                            val ingredientLinesString =
                                Uri.encode(recipe.ingredientLines.joinToString(","))

                            navController.navigate("recipe/$label/$image/$url/$ingredientLinesString/false")
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Connect to the internet",
                    fontSize = 18.sp,
                    fontFamily = Inter,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Please check your connection and try again.",
                    fontSize = 15.sp,
                    fontFamily = Inter,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, Outline),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Outline
                    ),
                    onClick = {
                        isOnline.value = isInternetAvailable(context)
                        viewModel.searchRecipes(query)
                        searchAttempted = true
                    }
                ) {
                    Text(
                        text = "RETRY",
                        fontSize = 14.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}