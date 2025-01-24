package com.example.projectakhir.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val vendors: List<Vendor>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModelVendor(private val vendorRepository: VendorRepository) : ViewModel() {
    var vendorUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getVendors()
    }

    fun getVendors() {
        viewModelScope.launch {
            vendorUiState = HomeUiState.Loading
            vendorUiState = try {
                HomeUiState.Success(vendorRepository.getVendors())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteVendor(idVendor: String) {
        viewModelScope.launch {
            try {
                vendorRepository.deleteVendor(idVendor)
                // Refresh the list after deletion
                getVendors()
            } catch (e: IOException) {
                vendorUiState = HomeUiState.Error
            } catch (e: HttpException) {
                vendorUiState = HomeUiState.Error
            }
        }
    }
}
