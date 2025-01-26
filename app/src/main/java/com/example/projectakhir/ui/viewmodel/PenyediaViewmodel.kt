package com.example.projectakhir.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.AcaraApplications
import com.example.projectakhir.ui.viewmodel.Klien.DetailViewModelKlien
import com.example.projectakhir.ui.viewmodel.Klien.HomeViewModelKlien
import com.example.projectakhir.ui.viewmodel.Klien.InsertViewModelKlien
import com.example.projectakhir.ui.viewmodel.Klien.UpdateViewModelKlien
import com.example.projectakhir.ui.viewmodel.acara.HomeViewModel
import com.example.projectakhir.ui.viewmodel.acara.InsertViewModel
import com.example.projectakhir.ui.viewmodel.acara.UpdateViewModel
import com.example.projectakhir.ui.viewmodel.lokasi.DetailViewModelLokasi2
import com.example.projectakhir.ui.viewmodel.lokasi.HomeViewModelLokasi
import com.example.projectakhir.ui.viewmodel.lokasi.InsertViewModelLokasi
import com.example.projectakhir.ui.viewmodel.lokasi.UpdateViewModellokasi
import com.example.projectakhir.ui.viewmodel.vendor.DetailViewModelVendor
import com.example.projectakhir.ui.viewmodel.vendor.HomeViewModelVendor
import com.example.projectakhir.ui.viewmodel.vendor.InsertViewModelVendor
import com.example.projectakhir.ui.viewmodel.vendor.UpdateViewModelVendor
import com.example.restapi.ui.viewmodel.DetailAcaraViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { InsertViewModel(aplikasiAcara().container.acaraRepository,aplikasiAcara().container.klienRepository,aplikasiAcara().container.lokasiRepository) }
        initializer { DetailAcaraViewModel(aplikasiAcara().container.acaraRepository) }
        initializer { UpdateViewModel(aplikasiAcara().container.acaraRepository, aplikasiAcara().container.klienRepository, aplikasiAcara().container.lokasiRepository) }
        initializer { HomeViewModelLokasi(aplikasiAcara().container.lokasiRepository) }
        initializer { InsertViewModelLokasi(aplikasiAcara().container.lokasiRepository) }
        initializer { DetailViewModelLokasi2(aplikasiAcara().container.lokasiRepository) }
        initializer { UpdateViewModellokasi(aplikasiAcara().container.lokasiRepository) }
        initializer { HomeViewModelKlien(aplikasiAcara().container.klienRepository) }
        initializer { InsertViewModelKlien(aplikasiAcara().container.klienRepository) }
        initializer { DetailViewModelKlien(aplikasiAcara().container.klienRepository) }
        initializer { UpdateViewModelKlien(aplikasiAcara().container.klienRepository) }
        initializer { HomeViewModelVendor(aplikasiAcara().container.vendorRepository) }
        initializer { InsertViewModelVendor(aplikasiAcara().container.vendorRepository) }
        initializer { DetailViewModelVendor(aplikasiAcara().container.vendorRepository) }
        initializer { UpdateViewModelVendor(aplikasiAcara().container.vendorRepository) }

    }
}

fun CreationExtras.aplikasiAcara(): AcaraApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AcaraApplications)
