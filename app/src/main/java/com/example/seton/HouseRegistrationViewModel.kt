package com.example.seton

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HouseRegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState.empty())
    val uiState: StateFlow<RegistrationState> = _uiState.asStateFlow()

    fun onChangeTitle(title: String) {
        _uiState.update { prev ->
            prev.copy(title = title)
        }
    }

    fun onChangeContent(content: String) {
        _uiState.update { prev ->
            prev.copy(content = content)
        }
    }

//    fun onChangedPassword(password: String) {
//        _uiState.update { prev ->
//            prev.copy(password = password)
//        }
//    }
}