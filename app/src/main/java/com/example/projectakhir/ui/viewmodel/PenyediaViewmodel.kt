package com.example.projectakhir.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.AcaraApplications
import com.example.projectakhir.ui.viewmodel.acara.DetailAcaraViewModel
import com.example.projectakhir.ui.viewmodel.acara.HomeViewModel
import com.example.projectakhir.ui.viewmodel.acara.InsertViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { InsertViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { DetailAcaraViewModel(aplikasiAcara().container.acaraRepository) }

    }
}

fun CreationExtras.aplikasiAcara(): AcaraApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AcaraApplications)
