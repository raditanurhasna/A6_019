package com.example.projectakhir.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.AcaraRepository
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.launch

class InsertViewModel(
    private val acaraRepo: AcaraRepository,
    private val klienRepo: KlienRepository,
    private val lokasiRepo: LokasiRepository
) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    var klienList by mutableStateOf<List<Klien>>(emptyList())
        private set

    var lokasiList by mutableStateOf<List<Lokasi>>(emptyList())
        private set

    var klienIds by mutableStateOf<List<String>>(emptyList())
        private set

    var lokasiIds by mutableStateOf<List<String>>(emptyList())
        private set

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