package com.example.projectakhir.ui.viewmodel.acara


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.repository.AcaraRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val acaraRepo: AcaraRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertAcaraState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertAcara() {
        viewModelScope.launch {
            try {
                acaraRepo.insertAcara(uiState.insertUiEvent.toAcara())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idacara: String = "",
    val namaacara: String = "",
    val deskripsiacara: String = "",
    val tanggalmulai: String = "",
    val tanggalberakhir: String = "",
    val idlokasi: String = "",
    val idklien: String = "",
    val statusacara: String = ""
)

fun InsertUiEvent.toAcara(): Acara = Acara(
    idacara = idacara,
    namaacara = namaacara,
    deskripsiacara = deskripsiacara,
    tanggalmulai = tanggalmulai,
    tanggalberakhir = tanggalberakhir,
    idlokasi = idlokasi,
    idklien = idklien,
    statusacara = statusacara
)

fun Acara.toUiStateAcara(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Acara.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idacara = idacara,
    namaacara = namaacara,
    deskripsiacara = deskripsiacara,
    tanggalmulai = tanggalmulai,
    tanggalberakhir = tanggalberakhir,
    idlokasi = idlokasi,
    idklien = idklien,
    statusacara = statusacara

)
