package com.example.projectakhir.ui.viewmodel.lokasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val lokasi: Lokasi) : DetailUiState() // Gantilah dengan model Lokasi
    object Error : DetailUiState()
}

class DetailViewModelLokasi2(private val repository: LokasiRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getLokasiById(idLokasi: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val lokasi = repository.getLokasiById(idLokasi)
                if (lokasi != null) {
                    _uiState.value = DetailUiState.Success(lokasi)
                } else {
                    _uiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error
            }
        }
    }
}
