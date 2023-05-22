@file:OptIn(ExperimentalMaterial3Api::class)

package com.dicoding.recipeapp.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.recipeapp.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateToWeb: (String) -> Unit
) {
    val username = stringResource(R.string.author_username)
    val profileText = stringResource(R.string.menu_profile)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = profileText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.surface
                    )
                },
                actions = {
                    IconButton(modifier = modifier.padding(10.dp), onClick = { navigateToWeb(username) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_www),
                            tint = MaterialTheme.colorScheme.surface,
                            contentDescription = stringResource(R.string.about_page)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            ProfileContent()
        }
    }
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(top = 64.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = stringResource(R.string.profile_picture_link),
            contentDescription = stringResource(R.string.desc_foto_profil),
            placeholder = painterResource(id = R.drawable.thumbnail_profile_picture),
            modifier = modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(R.string.author_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(R.string.author_email),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.secondary,
            modifier = modifier.padding(top = 8.dp)
        )
    }
}