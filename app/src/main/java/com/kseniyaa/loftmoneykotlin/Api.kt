package com.kseniyaa.loftmoneykotlin

import retrofit2.Call
import retrofit2.http.*
import java.util.LinkedHashMap

interface Api {

    @GET("items")
    abstract fun getItems(@Query("type") type: String?, @Query("auth-token") auth_token: String?): Call<List<Item>>

    @POST("items/add")
    abstract fun createItem(@Body item: Item?, @Query("auth-token") auth_token: String?): Call<Item>

    @POST("items/remove")
    abstract fun deleteItem(@Query("id") id: Int?, @Query("auth-token") auth_token: String?): Call<Item>

    @GET("auth")
    abstract fun getAuthToken(@Query("social_user_id") user_id: String?): Call<LinkedHashMap<String, String>>
    // TODO: 14.09.2018 get balance

}