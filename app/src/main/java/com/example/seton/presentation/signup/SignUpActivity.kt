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
                    navigateToMainActivity(token)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAppLink(intent)
    }

    private fun handleAppLink(intent: Intent) {
        Log.e("SignUpActivity", "Handling app link: $intent")
        intent.data?.let { uri: Uri ->
            when (uri.path) {
                "/callback" -> {
                    val token = uri.getQueryParameter("token")
                    Log.e("SignUpActivity", "Token: $token")
                    val refreshToken = uri.getQueryParameter("r")

                    if (token != null) {
                        Log.d("SignUpActivity", "Token: $token")
                        saveToken(token)
                        navigateToMainActivity(token)
                    } else {
                        Log.e("SignUpActivity", "Token is missing in the URI.")
                    }
                }
                else -> Log.e("SignUpActivity", "Unhandled URI path: ${uri.path}")
            }
        }
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("token", token)
            apply()
        }
    }

    private fun navigateToMainActivity(token: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("token", token) // 필요하면 MainActivity로 토큰 전달
        }
        startActivity(intent)
        finish()
    }
}