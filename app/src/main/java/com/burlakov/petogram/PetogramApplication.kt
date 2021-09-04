package com.burlakov.petogram

import android.app.Application
import com.burlakov.petogram.services.UserService
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PetogramApplication : Application() {
    companion object {
        lateinit var userService: UserService
    }

    override fun onCreate() {
        super.onCreate()

        configureRetrofit()
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val spec = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectionSpecs(spec)
            .addInterceptor(httpLoggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.68:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
    }


}