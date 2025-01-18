package com.example.seton.presentation.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.seton.MainActivity
import com.example.seton.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        handleAppLink(intent)

        binding.btnSignIn.setOnClickListener {
            openLoginWebPage()
        }

    }
    private fun handleAppLink(intent: Intent) {
        Log.e("SignInActivity", "Handling app link: $intent")
        Log.e("SignInActivity", "Handling app link: ${intent.data?.path}")
        intent.data?.let { uri: Uri ->
            // URI의 쿼리 파라미터를 가져옴
            val token = uri.getQueryParameter("token")
            //val rtoken = uri.getQueryParameter("r")
            Log.e("SignInActivity", "Token: $token")
            // 해당 화면으로 이동할 인텐트 생성
            val intent = Intent(this, MainActivity::class.java)
            val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString("token", token)
                apply()
            }
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAppLink(intent)
    }
    fun openLoginWebPage() {
        val url = "http://34.223.224.180:8080/oauth2/authorization/kakao"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}