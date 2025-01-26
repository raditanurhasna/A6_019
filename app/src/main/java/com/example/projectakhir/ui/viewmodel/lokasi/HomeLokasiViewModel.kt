package com.example.projectakhir.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class LokasiUiState {
    data class Success(val lokasi: List<Lokasi>) : LokasiUiState()
    object Error : LokasiUiState()
    object Loading : LokasiUiState()
}

class HomeViewModelLokasi(private val lokasiRepo: LokasiRepository) : ViewModel() {
    var lokasiUiState: LokasiUiState by mutableStateOf(LokasiUiState.Loading)
        private set

    init {
        getLokasi()
    }

    fun getLokasi() {
        viewModelScope.launch {
            lokasiUiState = LokasiUiState.Loading
            lokasiUiState = try {
                LokasiUiState.Success(lokasiRepo.getLokasi())
            } catch (e: IOException) {
                LokasiUiState.Error
            } catch (e: HttpException) {
                LokasiUiState.Error
            }
        }
    }

    fun deleteLokasi(idLokasi: String) {
        viewModelScope.launch {
            try {
                lokasiRepo.deleteLokasi(idLokasi)
                getLokasi()
            } catch (e: IOException) {
                lokasiUiState = LokasiUiState.Error
            } catch (e: HttpException) {
                lokasiUiState = LokasiUiState.Error
            }
        }
    }
}
