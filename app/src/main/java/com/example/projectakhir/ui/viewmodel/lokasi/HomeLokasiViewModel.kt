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

sealed class HomeUiState {
    data class Success(val lokasi: List<Lokasi>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModelLokasi(private val lokasiRepo: LokasiRepository) : ViewModel() {
    var lokasiUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getLokasi()
    }

    fun getLokasi() {
        viewModelScope.launch {
            lokasiUiState = HomeUiState.Loading
            lokasiUiState = try {
                HomeUiState.Success(lokasiRepo.getLokasi())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteLokasi(idLokasi: String) {
        viewModelScope.launch {
            try {
                lokasiRepo.deleteLokasi(idLokasi)
                getLokasi()
            } catch (e: IOException) {
                lokasiUiState = HomeUiState.Error
            } catch (e: HttpException) {
                lokasiUiState = HomeUiState.Error
            }
        }
    }
}
