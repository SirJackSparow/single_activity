@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.list.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.list.R
import com.example.list.vm.ListUiState
import com.example.list.vm.ListViewModel
import com.example.shared.utils.Purple200
import com.example.shared.utils.PurpleDark
import kotlinx.coroutines.launch


@Composable
fun ListScreen(vm: ListViewModel = hiltViewModel()) {

    val uiState by vm.uiState

    ListContent(
        uiState = uiState, nextPage = { vm.nextPage() }, modifier = Modifier, vm
    ) { userName ->
        vm.selectUser(userName)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ListContent(
    uiState: ListUiState,
    nextPage: () -> Unit,
    modifier: Modifier,
    listViewModel: ListViewModel,
    onSelect: (String) -> Unit
) {
    var value by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Best Match",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        scope.launch {
                            listViewModel.sortUser("best match")
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(thickness= 2.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Followers",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        scope.launch {
                            listViewModel.sortUser("followers")
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetShape = MaterialTheme.shapes.medium,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        FenSurface(
                            color = Color.White,
                            contentColor = Color.LightGray,
                            shape = MaterialTheme.shapes.small,
                            modifier = modifier
                                .fillMaxWidth()
                                .height(65.dp)
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            AppBarTextField(
                                value = value,
                                onValueChange = { newValue -> value = newValue },
                                hint = "search...",
                                modifier = Modifier.padding(
                                    start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(onSearch = {
                                    scope.launch {
                                        listViewModel.searchUser(value)
                                    }
                                    keyboardController?.hide()
                                })
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PurpleDark),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                                contentDescription = "Filter",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                listViewModel.searchUser(value)
                            }
                            keyboardController?.hide()
                        }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (uiState) {
                    is ListUiState.Loading -> {
                        CircularProgressIndicator()
                        keyboardController?.hide()
                    }
                    is ListUiState.Shows -> {
                        val stateList = rememberLazyListState()

                        val isScrollToEnd by remember {
                            derivedStateOf {
                                stateList.layoutInfo.visibleItemsInfo.lastOrNull()?.index == stateList.layoutInfo.totalItemsCount - 1
                            }
                        }

                        if (isScrollToEnd && uiState.loadNextPage.not()) {
                            nextPage()
                        }

                        LazyColumn(state = stateList, modifier = Modifier.background(Purple200)) {
                            items(uiState.users) { user ->
                                ListItem(user = user, onSelect = { onSelect(it) })
                            }
                            if (uiState.loadNextPage) {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .wrapContentWidth(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                        }

                    }
                    is ListUiState.Error -> {
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
}
