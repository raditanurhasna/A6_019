package com.example.projectakhir.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Acara(
    @SerialName("id_acara")
    val idacara: String,

    @SerialName("nama_acara")
    val namaacara: String,

    @SerialName("deskripsi_acara")
    val deskripsiacara: String,

    @SerialName("tanggal_mulai")
    val tanggalmulai: String,

    @SerialName("tanggal_berakhir")
    val tanggalberakhir: String,

    @SerialName("id_lokasi")
    val idlokasi: String,

    @SerialName("id_klien")
    val idklien: String,

    @SerialName("status_acara")
    val statusacara: String,


)

