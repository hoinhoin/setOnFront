package com.example.seton.presentation.signup

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: (String) -> Unit // 로그인 성공 시 토큰 처리 콜백
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val url = "http://34.223.224.180:8080/oauth2/authorization/kakao"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent) // 카카오 로그인 웹 페이지로 이동
        },
        modifier = modifier
    ) {
        Text(text = "카카오 로그인")
    }
}

