package com.example.seton.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.seton.data.House
import com.example.seton.di.RetrofitInstance
import com.example.seton.presentation.housedetail.HouseDetailActivity
import com.example.seton.widgets.BottomNavigation

@Composable
fun BookMarkScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var houses by remember { mutableStateOf<List<House>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // 데이터 로드
    LaunchedEffect(key1 = true) {
        try {
            val response = RetrofitInstance.getApiService(context).getLikedHouses()
            if (response.isSuccessful) {
                houses = response.body()?.houses ?: emptyList()
            } else {
                error = "데이터를 불러오는데 실패했습니다."
            }
        } catch (e: Exception) {
            error = e.localizedMessage
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Text(
                text = "좋아요한 집",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = error ?: "알 수 없는 오류가 발생했습니다.",
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }
                houses.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "좋아요한 집이 없습니다.",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(houses) { house ->
                            HouseItem(
                                house = house,
                                onItemClick = { houseId ->
                                    val intent = Intent(context, HouseDetailActivity::class.java).apply {
                                        putExtra("houseId", houseId)
                                    }
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}