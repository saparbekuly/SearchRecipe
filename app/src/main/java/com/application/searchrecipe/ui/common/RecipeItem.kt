package com.application.searchrecipe.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.application.searchrecipe.data.remote.Recipe
import com.application.searchrecipe.utils.Inter


@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(8.dp)
            .height(200.dp)
            .fillMaxWidth()
            .shadow(4.dp)
            .clickable { onClick() }
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = recipe.label,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                text = recipe.label,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = Inter,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 8f,
                        offset = Offset(5f, 10f)
                    )
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}