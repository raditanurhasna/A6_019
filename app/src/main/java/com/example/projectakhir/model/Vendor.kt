package com.example.projectakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vendor(
    @SerialName("id_vendor")
    val idVendor: String,

    @SerialName("nama_vendor")
    val namaVendor: String,

    @SerialName("jenis_vendor")
    val jenisVendor: String,

    @SerialName("kontak_vendor")
    val kontakVendor: String
)

