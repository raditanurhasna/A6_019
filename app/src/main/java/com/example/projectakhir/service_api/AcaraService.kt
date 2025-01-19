package com.example.projectakhir.service_api

import com.example.projectakhir.model.Acara
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AcaraService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("insertacara.php")
    suspend fun insertAcara(@Body acara: Acara)

    @GET("bacaacara.php")
    suspend fun getAllAcara(): List<Acara>

    @GET("baca1acara.php/{id_acara}")
    suspend fun getAcaraById(@Query("id_acara") idAcara: String): Acara

    @PUT("editacara.php/{id_acara}")
    suspend fun updateAcara(@Query("id_acara") idAcara: String, @Body acara: Acara)

    @DELETE("deleteacara.php/{id_acara}")
    suspend fun deleteAcara(@Query("id_acara") idAcara: String): Response<Void>
}
