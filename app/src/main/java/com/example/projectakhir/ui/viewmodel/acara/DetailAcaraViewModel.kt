package com.example.restapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.repository.AcaraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val acara: Acara) : DetailUiState()
    object Error : DetailUiState()
}

class DetailAcaraViewModel(private val repository: AcaraRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getAcaraById(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val acara = repository.getAcarabyId(id)
                if (acara != null) {
                    _uiState.value = DetailUiState.Success(acara)
                } else {
                    _uiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error
            }
        }
    }

}
