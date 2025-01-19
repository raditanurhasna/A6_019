package com.example.projectakhir.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.repository.AcaraRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState {
    data class Success(val acara: List<Acara>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel(private val acaraRepository: AcaraRepository) : ViewModel() {
    var acaraUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAcara()
    }

    fun getAcara() {
        viewModelScope.launch {
            acaraUiState = HomeUiState.Loading
            acaraUiState = try {
                HomeUiState.Success(acaraRepository.getAcara())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteAcara(id: String) {
        viewModelScope.launch {
            try {
                acaraRepository.deleteAcara(id)
            } catch (e: IOException) {
                acaraUiState = HomeUiState.Error
            } catch (e: HttpException) {
                acaraUiState = HomeUiState.Error
            }
        }
    }
}
