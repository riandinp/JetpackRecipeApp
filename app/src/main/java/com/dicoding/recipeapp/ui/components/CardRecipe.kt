package com.dicoding.recipeapp.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.dicoding.recipeapp.R
import com.dicoding.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun CardRecipe(
    photoUrl: String,
    title: String,
    creator: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    Column(modifier = modifier) {
        AsyncImage(
            model = photoUrl,
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.thumbnail),
            contentScale = ContentScale.Crop,
            imageLoader = ImageLoader(context),
            modifier = Modifier
                .size(170.dp)
                .clip(ShapeDefaults.Medium)
                .shadow(elevation = 4.dp)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.create_by, creator),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = modifier.padding(top = 4.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CardRecipePreview() {
    RecipeAppTheme {
        CardRecipe(
            photoUrl = "https://cdn.yummy.co.id/content-images/images/20201124/ec8PEF2w4v0cPrA5A0SE4WFJfOUWV7M8-31363036323034393234d41d8cd98f00b204e9800998ecf8427e.jpg",
            title = "Tahu Bulat",
            creator = "Riandi NP"
        )
    }
}