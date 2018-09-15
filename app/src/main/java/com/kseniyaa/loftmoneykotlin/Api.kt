package com.kseniyaa.loftmoneykotlin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface Api {

    @GET("items")
    fun getItems(@Query("type") type: String?, @Query("auth-token") auth_token: String?): Call<List<Item>>

    @POST("items/add")
    fun createItem(@Body item: Item?, @Query("auth-token") auth_token: String?): Call<Item>

    @POST("items/remove")
    fun deleteItem(@Query("id") id: Int?, @Query("auth-token") auth_token: String?): Call<Item>

    @GET("auth")
    fun getAuthToken(@Query("social_user_id") user_id: String?): Call<LinkedHashMap<String, String>>
    // TODO: 14.09.2018 get balance

}