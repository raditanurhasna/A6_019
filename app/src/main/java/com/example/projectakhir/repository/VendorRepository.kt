package com.example.projectakhir.repository

import com.example.projectakhir.model.Vendor
import com.example.projectakhir.service_api.VendorService
import okio.IOException

interface VendorRepository {
    suspend fun insertVendor(vendor: Vendor)

    suspend fun getVendors(): List<Vendor>

    suspend fun updateVendor(idVendor: String, vendor: Vendor)

    suspend fun deleteVendor(idVendor: String)

    suspend fun getVendorById(idVendor: String): Vendor
}

class NetworkVendorRepository(
    private val vendorApiService: VendorService
) : VendorRepository {
    override suspend fun insertVendor(vendor: Vendor) {
        vendorApiService.insertVendor(vendor)
    }

    override suspend fun updateVendor(idVendor: String, vendor: Vendor) {
        vendorApiService.updateVendor(idVendor, vendor)
    }

    override suspend fun deleteVendor(idVendor: String) {
        try {
            val response = vendorApiService.deleteVendor(idVendor)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete vendor. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getVendors(): List<Vendor> =
        vendorApiService.getAllVendors()

    override suspend fun getVendorById(idVendor: String): Vendor {
        return vendorApiService.getVendorById(idVendor)
    }
}
