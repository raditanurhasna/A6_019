package com.example.projectakhir.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Klien (
    @SerialName("id_klien")
    val idKlien: String,

    @SerialName("nama_klien")
    val namaKlien: String,

    @SerialName("kontak_klien")
    val kontakKlien: String
)
