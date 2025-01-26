package com.example.projectakhir.ui.viewmodel.Klien

import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import kotlinx.coroutines.launch
import okio.IOException

sealed class KlienUiState {
    data class Success(val klien: List<Klien>) : KlienUiState()
    object Error : KlienUiState()
    object Loading : KlienUiState()
}

class HomeViewModelKlien(private val klienRepository: KlienRepository) : ViewModel() {
    var klienUiState: KlienUiState by mutableStateOf(KlienUiState.Loading)
        private set

    init {
        getKlien()
    }

    fun getKlien() {
        viewModelScope.launch {
            klienUiState = KlienUiState.Loading
            klienUiState = try {
                KlienUiState.Success(klienRepository.getKlien())
            } catch (e: IOException) {
                KlienUiState.Error
            } catch (e: HttpException) {
                KlienUiState.Error
            }
        }
    }

    fun deleteKlien(idKlien: String) {
        viewModelScope.launch {
            try {
                klienRepository.deleteKlien(idKlien)
                getKlien() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                klienUiState = KlienUiState.Error
            } catch (e: HttpException) {
                klienUiState = KlienUiState.Error
            }
        }
    }
}
