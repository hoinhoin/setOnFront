package com.example.seton.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.seton.widgets.BottomNavigation

@Composable
fun BookMarkScreen(
    navController: NavController
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "예시",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1, // 텍스트를 한 줄로 제한
                overflow = TextOverflow.Ellipsis, // 한 줄을 초과하면 '...'로 표시
                modifier = Modifier.semantics { contentDescription = "홈 화면 내용 글씨 크기" }
            )
        }
    }
}