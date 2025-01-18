package com.example.seton.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.seton.R
import com.example.seton.widgets.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {Text("마이페이지")})
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Image(painter = painterResource(id = R.drawable.groupp),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 130.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextButton(
                onClick = {}
            ) {
                Text("로그아웃 하기")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                ) {

                Text(

                    text = "내가 등록한 빈집",
                    style = TextStyle(
                        fontSize = 24.sp
                    )
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text (
                    text = "최신순으로 등록한 빈집입니다.",
                    style = TextStyle(
                        //fontSize = 10.sp,
                        fontWeight = FontWeight.Thin
                    )

                )
            }
            // LazyColumn을 사용한 리스트 표시
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(10) { // 샘플로 10개의 빈 박스를 생성
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .border(1.dp, color = androidx.compose.ui.graphics.Color.Gray) // 아웃라인
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(24.dp)) // 곡률 추가

                    ) {
                        Text(text = "빈집")
                    }
                }
            }

        }
    }
}