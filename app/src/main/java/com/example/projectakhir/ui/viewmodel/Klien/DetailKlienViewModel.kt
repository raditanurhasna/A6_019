package com.example.projectakhir.ui.viewmodel.Klien

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val klien: Klien) : DetailUiState()
    object Error : DetailUiState()
}

class DetailViewModelKlien(private val repository: KlienRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getKlienById(idKlien: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val klien = repository.getKlienById(idKlien)
                if (klien != null) {
                    _uiState.value = DetailUiState.Success(klien)
                } else {
                    _uiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error
            }
        }
    }
}
