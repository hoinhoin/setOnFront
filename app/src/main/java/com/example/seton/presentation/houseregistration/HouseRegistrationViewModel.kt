package com.example.seton.presentation.houseregistration

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HouseRegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState.empty())
    val uiState: StateFlow<RegistrationState> = _uiState.asStateFlow()

    fun onChangeTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun onChangeContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun onChangeLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun onChangeStartArea(startArea: String) {
        _uiState.update { it.copy(startArea = startArea) }
    }

    fun onChangeEndArea(endArea: String) {
        _uiState.update { it.copy(endArea = endArea) }
    }

    fun onChangePrice(price: String) {
        _uiState.update { it.copy(price = price) }
    }

    fun onChangePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onTagSelected(tag: String) {
        _uiState.update { it.copy(tags = _uiState.value.tags + tag) }
    }
}