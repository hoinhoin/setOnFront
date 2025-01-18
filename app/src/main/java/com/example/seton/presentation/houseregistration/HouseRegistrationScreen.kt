package com.example.seton.presentation.houseregistration

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.seton.R

@Composable
fun HouseRegistrationScreen(
    viewModel: HouseRegistrationViewModel,
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "집 등록",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 이미지
        item {
            Text(text = "이미지", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Gray)
            ) {
                Text(
                    text = "이미지 업로드 (예시)",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 제목
        item {
            TextField(
                value = state.title,
                onValueChange = { viewModel.onChangeTitle(it) },
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 내용
        item {
            TextField(
                value = state.content,
                onValueChange = { viewModel.onChangeContent(it) },
                label = { Text("내용") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

        }

        // 위치
        item {
            TextField(
                value = state.location,
                onValueChange = { viewModel.onChangeLocation(it) },
                label = { Text("위치") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 평수
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextField(
                    value = state.startArea,
                    onValueChange = { viewModel.onChangeStartArea(it) },
                    label = { Text("시작 평수") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = state.endArea,
                    onValueChange = { viewModel.onChangeEndArea(it) },
                    label = { Text("끝 평수") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }


        // 가격
        item {
            TextField(
                value = state.price,
                onValueChange = { viewModel.onChangePrice(it) },
                label = { Text("최저 가격") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 전화번호
        item {
            TextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.onChangePhoneNumber(it) },
                label = { Text("전화번호") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 태그
        item {
            Text(text = "태그", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Chip(label = "마당 있는 집", onClick = { viewModel.onTagSelected("마당 있는 집") })
                Chip(label = "테라스 있는 집", onClick = { viewModel.onTagSelected("테라스 있는 집") })
                Chip(label = "넓은 집", onClick = { viewModel.onTagSelected("넓은 집") })
                Chip(label = "작업실로 적합", onClick = { viewModel.onTagSelected("작업실로 적합") })
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 등록 버튼
        item {
            Button(
                onClick = {
                    Log.d("HouseRegistration", state.toString())
                    onConfirm()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("등록")
            }
        }
    }
}

// Chip 컴포저블
@Composable
fun Chip(label: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
