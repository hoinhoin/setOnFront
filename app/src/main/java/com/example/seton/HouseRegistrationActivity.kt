package com.example.seton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class HouseRegistrationActivity : ComponentActivity() {

    private val viewModel = HouseRegistrationViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HouseRegistrationScreen(
                viewModel = viewModel,
                onConfirm = {
                    setResult(RESULT_OK)
                    finish()
                }
            )
        }
    }
}