package com.example.projectakhir.ui.viewmodel.lokasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idLokasi: String,
    val namaLokasi: String,
    val alamatLokasi: String,
    val kapasitas: String
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val lokasi: Lokasi) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateViewModellokasi(
    private val lokasiRepository: LokasiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadLokasiData(idLokasi: String) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val lokasi = lokasiRepository.getLokasiById(idLokasi) // Mengambil data lokasi
                currentFormData = UpdateUiEvent(
                    idLokasi = lokasi.idLokasi,
                    namaLokasi = lokasi.namaLokasi,
                    alamatLokasi = lokasi.alamatLokasi,
                    kapasitas = lokasi.kapasitas
                )
                _uiState.value = UpdateUiState.Success(lokasi)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load lokasi data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateLokasi() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val lokasi = Lokasi(
                    idLokasi = formData.idLokasi,
                    namaLokasi = formData.namaLokasi,
                    alamatLokasi = formData.alamatLokasi,
                    kapasitas = formData.kapasitas
                )
                lokasiRepository.updateLokasi(formData.idLokasi, lokasi) // Memperbarui data lokasi
                _uiState.value = UpdateUiState.Success(lokasi)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update lokasi: ${e.message}")
            }
        }
    }
}
