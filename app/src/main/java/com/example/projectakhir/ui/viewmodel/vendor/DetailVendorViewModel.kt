package com.example.projectakhir.ui.viewmodel.vendor

import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val vendor: Vendor) : DetailUiState()
    object Error : DetailUiState()
}

class DetailViewModelVendor(private val repository: VendorRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getVendorById(idVendor: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val vendor = repository.getVendorById(idVendor)
                if (vendor != null) {
                    _uiState.value = DetailUiState.Success(vendor)
                } else {
                    _uiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error
            }
        }
    }
}
