package com.burlakov.petogram

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.burlakov.petogram.model.User
import com.burlakov.petogram.services.UserService
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PetogramApplication : Application() {

    companion object {
        lateinit var userService: UserService
        var user: User? = null
        val baseUrl : String = "http://192.168.50.237:8080/"
    }

    override fun onCreate() {
        super.onCreate()

        configureRetrofit()
        getCurrentUser()
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
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
    }

    private fun getCurrentUser() {
        val pref = this.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val id = pref.getLong("id", -1)
        if (id != -1L) {
            user = User(
                pref.getString("email", "")!!,
                pref.getString("password", "")!!
            )
            user?.id = id
            user?.active = true
            user?.verificationCode = null
            val username = pref.getString("username", "")
            if (!username.equals("")) {
                user?.username = username
            }
            val avatar = pref.getString("avatar", "")
            if (!avatar.equals("")) {
                user?.avatar = avatar
            }
        }
    }
}