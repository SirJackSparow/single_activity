package com.example.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.detail.vm.DetailUiState
import com.example.detail.vm.DetailViewModel
import com.example.shared.data.databases.model.UserModel
import com.example.shared.utils.Purple200
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(vm: DetailViewModel = hiltViewModel()) {
    val uiState by vm.uiState
    DetailContent(
        modifier = Modifier,
        navigateUp = { vm.navigateUp() },
        isFavourite = vm.isFavorite.value,
        uiState = uiState,
        deleteData = { vm.deleteData() },
        addData = { vm.addData() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailContent(
    modifier: Modifier,
    navigateUp: () -> Unit,
    isFavourite: Boolean,
    uiState: DetailUiState,
    deleteData: () -> Unit,
    addData: () -> Unit
) {

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple200),
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (isFavourite) {
                                    deleteData()
                                } else {
                                    addData()
                                }
                            }
                        },
                        content = {
                            if (isFavourite) {
                                Icon(
                                    Icons.Default.Favorite,
                                    tint = Color.Red,
                                    contentDescription = "Favourite"
                                )
                            } else {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Favourite",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is DetailUiState.Shows -> {

                    Column {
                        Box(Modifier.fillMaxSize()) {
                            Spacer(
                                modifier = Modifier
                                    .height(160.dp)
                                    .fillMaxWidth()
                                    .background(Purple200)
                            )
                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Spacer(modifier = Modifier.height(20.dp))
                                AsyncImage(
                                    model = uiState.users.avatar_url,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "User Image",
                                    modifier = Modifier
                                        .size(250.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = uiState.users.login,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = uiState.users.type,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                                    Text(
                                        text = uiState.users.blog,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Divider(thickness = 2.dp, color = Color.DarkGray)
                            }
                        }
                    }
                }
                is DetailUiState.Error -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.message)
                    }
                }
            }
        }
    }
}
