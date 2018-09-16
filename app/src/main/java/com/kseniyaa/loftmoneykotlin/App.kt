package com.kseniyaa.loftmoneykotlin

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var api: Api

    override fun onCreate() {
        super.onCreate()

        val level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = level

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://android.loftschool.com/basic/v1/")
                //.baseUrl("https://verdant-violet.glitch.me")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        api = retrofit.create(Api::class.java)
    }
}

