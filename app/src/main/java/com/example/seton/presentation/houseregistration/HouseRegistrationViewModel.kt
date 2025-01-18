package com.example.seton.presentation.houseregistration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HouseRegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState.empty())
    val uiState: StateFlow<RegistrationState> = _uiState.asStateFlow()

    // 태그 입력 상태 관리
    var tagInput by mutableStateOf("")
        private set

    // 제목 변경
    fun onChangeTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    // 내용 변경
    fun onChangeContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    // 위치 변경
    fun onChangeLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    // 시작 평수 변경
    fun onChangeStartArea(startArea: String) {
        _uiState.update { it.copy(startArea = startArea) }
    }

    // 끝 평수 변경
    fun onChangeEndArea(endArea: String) {
        _uiState.update { it.copy(endArea = endArea) }
    }

    // 가격 변경
    fun onChangePrice(price: String) {
        _uiState.update { it.copy(price = price) }
    }

    // 전화번호 변경
    fun onChangePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    // 태그 입력 처리
    fun onTagInputChanged(input: String) {
        tagInput = input
        if (input.endsWith(",")) {
            val newTag = input.trimEnd(',').trim()
            if (newTag.isNotEmpty() && !_uiState.value.tags.contains(newTag)) {
                _uiState.update { it.copy(tags = it.tags + newTag) }
            }
            tagInput = "" // 입력 필드 초기화
        }
    }

    // 태그 삭제
    fun removeTag(tag: String) {
        _uiState.update { it.copy(tags = it.tags - tag) }
    }
}
