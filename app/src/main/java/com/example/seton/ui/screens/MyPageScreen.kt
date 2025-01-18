package com.example.seton.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.seton.R
import com.example.seton.data.MyPageResponse
import com.example.seton.di.RetrofitInstance
import com.example.seton.presentation.housedetail.HouseDetailActivity
import com.example.seton.widgets.BottomNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var myPageData by remember { mutableStateOf<MyPageResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope() // 코루틴 스코프 추가

    // Activity를 안전하게 종료하기 위한 확장 함수
    fun Context.finishAffinity() {
        val activity = (this as? Activity) ?: return
        activity.finishAffinity()
    }

    LaunchedEffect(key1 = true) {
        try {
            val response = RetrofitInstance.getApiService(context).getMyPage()
            if (response.isSuccessful) {
                myPageData = response.body()
                isLoading = false
            } else {
                error = "데이터를 불러오는데 실패했습니다."
            }
        } catch (e: Exception) {
            error = e.localizedMessage
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("마이페이지") })
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                // 로고 이미지
                Image(
                    painter = painterResource(id = R.drawable.groupp),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )

                // 프로필 섹션
                myPageData?.user?.let { user ->
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = user.profileImageUrl,
                            contentDescription = "프로필 이미지",
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                TextButton(
                    onClick = {
                        scope.launch {
                            delay(1000) // 1초 딜레이
                            context.finishAffinity() // 앱 종료
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("로그아웃 하기")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "내가 등록한 빈집",
                        style = TextStyle(fontSize = 24.sp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "최신순으로 등록한 빈집입니다.",
                        style = TextStyle(fontWeight = FontWeight.Thin)
                    )
                }

                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    error != null -> {
                        Text(
                            text = error ?: "알 수 없는 오류가 발생했습니다.",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            myPageData?.houses?.let { houses ->
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
    }
}