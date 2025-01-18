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

    // 카카오 로그인 버튼 UI
    Button(
        onClick = {
            val url = "https://kauth.kakao.com/oauth/authorize?client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent) // 카카오 로그인 웹 페이지로 이동
        },
        modifier = modifier
    ) {
        Text(text = "카카오 로그인")
    }
}

