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

sealed class HomeUiState {
    data class Success(val klien: List<Klien>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModelKlien(private val klienRepository: KlienRepository) : ViewModel() {
    var klienUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getKlien()
    }

    fun getKlien() {
        viewModelScope.launch {
            klienUiState = HomeUiState.Loading
            klienUiState = try {
                HomeUiState.Success(klienRepository.getKlien())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteKlien(idKlien: String) {
        viewModelScope.launch {
            try {
                klienRepository.deleteKlien(idKlien)
                getKlien() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                klienUiState = HomeUiState.Error
            } catch (e: HttpException) {
                klienUiState = HomeUiState.Error
            }
        }
    }
}
