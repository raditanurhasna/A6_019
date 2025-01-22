package com.example.projectakhir.repository

import com.example.projectakhir.model.Klien
import com.example.projectakhir.service_api.KlienService
import okio.IOException

interface KlienRepository {
    suspend fun insertKlien(klien: Klien)

    suspend fun getKlien(): List<Klien>

    suspend fun updateKlien(idKlien: String, klien: Klien)

    suspend fun deleteKlien(idKlien: String)

    suspend fun getKlienById(idKlien: String): Klien
}

class NetworkKlienRepository(
    private val klienApiService: KlienService
) : KlienRepository {
    override suspend fun insertKlien(klien: Klien) {
        klienApiService.insertKlien(klien)
    }

    override suspend fun updateKlien(idKlien: String, klien: Klien) {
        klienApiService.updateKlien(idKlien, klien)
    }

    override suspend fun deleteKlien(idKlien: String) {
        try {
            val response = klienApiService.deleteKlien(idKlien)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Klien. HTTP Status Code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKlien(): List<Klien> =
        klienApiService.getAllKlien()

    override suspend fun getKlienById(idKlien: String): Klien {
        return klienApiService.getKlienById(idKlien)
    }
}
