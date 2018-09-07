package com.kseniyaa.loftmoneykotlin

import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("items")
    fun getItems(@Query("type") type: String?): Call<ItemsData>

    @POST("items/add")
    fun createItem(@Body item: Item): Call<Item>


    @DELETE("items/{id}")
    fun deleteItem(@Path("id") id: Int): Call<Item>

}