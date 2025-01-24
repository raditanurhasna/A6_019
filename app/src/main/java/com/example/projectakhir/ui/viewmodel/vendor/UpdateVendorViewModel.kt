package com.example.projectakhir.ui.viewmodel.vendor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idVendor: String,
    val namaVendor: String,
    val jenisVendor: String,
    val kontakVendor: String,
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val vendor: Vendor) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateViewModelVendor(
    private val vendorRepository: VendorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadVendorData(idVendor: String) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val vendor = vendorRepository.getVendorById(idVendor)
                currentFormData = UpdateUiEvent(
                    idVendor = vendor.idVendor,
                    namaVendor = vendor.namaVendor,
                    jenisVendor = vendor.jenisVendor,
                    kontakVendor = vendor.kontakVendor
                )
                _uiState.value = UpdateUiState.Success(vendor)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load vendor data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateVendor() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val vendor = Vendor(
                    idVendor = formData.idVendor,
                    namaVendor = formData.namaVendor,
                    jenisVendor = formData.jenisVendor,
                    kontakVendor = formData.kontakVendor
                )
                vendorRepository.updateVendor(formData.idVendor, vendor)
                _uiState.value = UpdateUiState.Success(vendor)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update vendor: ${e.message}")
            }
        }
    }
}
