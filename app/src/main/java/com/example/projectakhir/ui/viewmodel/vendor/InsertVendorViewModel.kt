package com.example.projectakhir.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import kotlinx.coroutines.launch

class InsertViewModelVendor(private val vendorRepository: VendorRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertVendorState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    fun insertVendor() {
        viewModelScope.launch {
            try {
                val vendor = uiState.insertUiEvent.toVendor()
                vendorRepository.insertVendor(vendor)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(error = e.message)
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val error: String? = null
)

data class InsertUiEvent(
    val idVendor: String = "",
    val namaVendor: String = "",
    val jenisVendor: String = "",
    val kontakVendor: String = ""
)

fun InsertUiEvent.toVendor(): Vendor = Vendor (
    idVendor = idVendor,
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    kontakVendor = kontakVendor
)

fun Vendor.toUiStateVendor(): InsertUiState = InsertUiState(
    insertUiEvent = this.toInsertUiEvent()
)

fun Vendor.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idVendor = idVendor,
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    kontakVendor = kontakVendor

)
