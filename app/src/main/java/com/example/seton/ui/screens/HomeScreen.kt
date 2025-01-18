package com.example.seton.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.seton.presentation.houseregistration.HouseRegistrationActivity
import com.example.seton.widgets.BottomNavigation
@Composable
fun HomeScreen(
    navController: NavController,
) {

    val context = LocalContext.current
  Scaffold (
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

      }
  }
    // HouseRegistrationAcitivity로 이동하는 버튼을 추가하세요.
    // 버튼을 클릭하면 navController.navigate("houseRegistration")을 호출하세요.
    // 버튼의 텍스트는 "Go to House Registration"으로 설정하세요.
    // 버튼의 크기는 200dp x 50dp로 설정하세요.
    // 버튼의 색상은 Color.Blue로 설정하세요.
    // 버튼의 텍스트 색상은 Color.White로 설정하세요.
    // 버튼의 모서리는 8dp로 설정하세요.
    // 버튼의 텍스트 크기는 16sp로 설정하세요.
    Button(
        onClick = { val intent = Intent(context, HouseRegistrationActivity::class.java)
            startActivity(context, intent, null) },
        modifier = Modifier
            .padding(16.dp)
            .size(200.dp, 50.dp),
        shape = RoundedCornerShape(8.dp),
        content = {
            Text(
                text = "Go to House Registration",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    )
}
