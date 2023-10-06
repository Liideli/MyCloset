@file:OptIn(ExperimentalFoundationApi::class)

package com.example.mycloset

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyHomeScreen() {
    val icons = listOf(
        painterResource(id = R.drawable.outline_camera_alt_24)
    )
    Scaffold(
        topBar = {
            TopAppBar(
//                colors = topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
                title = {
                    Text("MyCloset")
                }
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO navigation to the Scanner*/ }){
//                        Icon(icons[0])
//                    }
//                }
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You don't have any items yet...",
                textAlign = TextAlign.Center,
//            modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(topBar = {
        TopAppBar(
//                colors = topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
            title = {
                Text("MyCloset", modifier = Modifier.padding(2.dp))
            },
            navigationIcon = {
                IconButton(onClick = { /*TODO navigation to the Scanner*/ }) {
                }
            }
        )
    }) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(10) {
                ItemCard()
            }
        }
    }
}

@Preview
@Composable
fun PrevHome() {
    HomeScreen()
}

@Preview
@Composable
fun PreEmpty() {
    EmptyHomeScreen()
}

