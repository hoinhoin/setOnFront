package com.example.seton.presentation.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.seton.MainActivity

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 앱 링크 처리
        handleAppLink(intent)

        setContent {
            SignUpScreen(
                onLoginSuccess = { token ->
                    // 로그인 성공 시 MainActivity로 이동
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAppLink(intent)
    }

    private fun handleAppLink(intent: Intent) {
        intent.data?.let { uri: Uri ->
            when (uri.path) {
                "/token" -> {
                    // URI의 쿼리 파라미터를 가져옴
                    val token = uri.getQueryParameter("t")
                    val rtoken = uri.getQueryParameter("r")

                    // 토큰을 저장
                    if (token != null) {
                        // "token" 키로 토큰을 저장
                        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("token", token)
                            apply()
                        }

                        // MainActivity로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("SignUpActivity", "Token is missing in the URI.")
                    }
                }
                else -> {
                    Log.e("SignUpActivity", "Unhandled URI path: ${uri.path}")
                }
            }
        }
    }
}
