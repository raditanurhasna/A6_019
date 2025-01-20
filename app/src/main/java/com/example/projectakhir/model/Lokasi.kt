package com.example.projectakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lokasi(
    @SerialName("id_lokasi")
    val idLokasi: String,

    @SerialName("nama_lokasi")
    val namaLokasi: String,

    @SerialName("alamat_lokasi")
    val alamatLokasi: String,

    @SerialName("kapasitas")
    val kapasitas: String,
)
