package com.kseniyaa.loftmoneykotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("items")
    fun getItems(@Query("type") type: String?): Call<List<Item>>


}