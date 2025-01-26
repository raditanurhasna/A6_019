package com.example.projectakhir.ui.viewmodel.acara

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.AcaraRepository
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idacara: String,
    val namaacara: String,
    val deskripsiacara: String,
    val tanggalmulai: String,
    val tanggalberakhir: String,
    val idlokasi: String,
    val idklien: String,
    val statusacara: String,
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val acara: Acara) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateViewModel(
    private val acaraRepository: AcaraRepository,
    private val klienRepo: KlienRepository,
    private val lokasiRepo: LokasiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    var klienList: List<Klien> = emptyList()
    var lokasiList: List<Lokasi> = emptyList()

    var klienIds: List<String> = emptyList()
    var lokasiIds: List<String> = emptyList()

    init {
        fetchKlienList()
        fetchLokasiList()
    }

    private fun fetchKlienList() {
        viewModelScope.launch {
            try {
                klienList = klienRepo.getKlien()
                klienIds = klienList.map { it.idKlien }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchLokasiList() {
        viewModelScope.launch {
            try {
                lokasiList = lokasiRepo.getLokasi()
                lokasiIds = lokasiList.map { it.idLokasi }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadAcaraData(id: String) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val acara = acaraRepository.getAcarabyId(id)
                currentFormData = UpdateUiEvent(
                    idacara = acara.idacara,
                    namaacara = acara.namaacara,
                    deskripsiacara = acara.deskripsiacara,
                    tanggalmulai = acara.tanggalmulai,
                    tanggalberakhir = acara.tanggalberakhir,
                    idlokasi = acara.idlokasi,
                    idklien = acara.idklien,
                    statusacara = acara.statusacara
                )
                _uiState.value = UpdateUiState.Success(acara)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load acara data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateAcara() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val acara = Acara(
                    idacara = formData.idacara,
                    namaacara = formData.namaacara,
                    deskripsiacara = formData.deskripsiacara,
                    tanggalmulai = formData.tanggalmulai,
                    tanggalberakhir = formData.tanggalberakhir,
                    idlokasi = formData.idlokasi,
                    idklien = formData.idklien,
                    statusacara = formData.statusacara
                )
                acaraRepository.updateAcara(formData.idacara, acara)
                _uiState.value = UpdateUiState.Success(acara)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update acara: ${e.message}")
            }
        }
    }
}