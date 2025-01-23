package com.example.projectakhir.ui.viewmodel.Klien

import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idKlien: String,
    val namaKlien: String,
    val kontakKlien: String,
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val klien: Klien) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateViewModelKlien(
    private val klienRepository: KlienRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadKlienData(idKlien: String) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val klien = klienRepository.getKlienById(idKlien)
                currentFormData = UpdateUiEvent(
                    idKlien = klien.idKlien,
                    namaKlien = klien.namaKlien,
                    kontakKlien = klien.kontakKlien,

                )
                _uiState.value = UpdateUiState.Success(klien)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load client data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateKlien() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val klien = Klien(
                    idKlien = formData.idKlien,
                    namaKlien = formData.namaKlien,
                    kontakKlien = formData.kontakKlien,
                )
                klienRepository.updateKlien(formData.idKlien, klien)
                _uiState.value = UpdateUiState.Success(klien)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update client: ${e.message}")
            }
        }
    }
}
