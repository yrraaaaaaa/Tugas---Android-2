package com.example.androidmysql

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
interface ApiEndpoint {
    @GET("tampilSemua.php")
    fun data() : Call<ReadModel>
    @FormUrlEncoded
    @POST("tambah.php")
    fun create(
        @Field("name") name: String,
        @Field("number") number: String
    ) : Call<SubmitModel>
    @FormUrlEncoded
    @POST("ubah.php")
    fun update(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("number") number: String
    ) : Call<SubmitModel>
    @FormUrlEncoded
    @POST("hapus.php")
    fun delete(
        @Field("id") id: String
    ) : Call<SubmitModel>
}