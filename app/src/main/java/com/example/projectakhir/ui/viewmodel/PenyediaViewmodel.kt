package com.example.projectakhir.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.AcaraApplications
import com.example.projectakhir.ui.viewmodel.acara.HomeViewModel
import com.example.projectakhir.ui.viewmodel.acara.InsertViewModel
import com.example.projectakhir.ui.viewmodel.acara.UpdateViewModel
import com.example.projectakhir.ui.viewmodel.lokasi.DetailViewModel
import com.example.projectakhir.ui.viewmodel.lokasi.HomeViewModelLokasi
import com.example.projectakhir.ui.viewmodel.lokasi.InsertViewModelLokasi
import com.example.restapi.ui.viewmodel.DetailAcaraViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { InsertViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { DetailAcaraViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { UpdateViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { HomeViewModelLokasi(aplikasiAcara().container.lokasiRepository) }
        initializer { InsertViewModelLokasi(aplikasiAcara().container.lokasiRepository) }
        initializer { DetailViewModel(aplikasiAcara().container.lokasiRepository) }

    }
}

fun CreationExtras.aplikasiAcara(): AcaraApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AcaraApplications)
