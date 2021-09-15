package com.burlakov.petogram.services

import com.burlakov.petogram.model.Answer
import com.burlakov.petogram.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.io.File
import java.io.InputStream


interface UserService {
    @POST("/singUp")
    suspend fun singUp(@Body user: User): Answer

    @POST("/logIn")
    suspend fun logIn(@Body user: User): Answer

    @POST("/checkUser")
    suspend fun checkUser(@Body user: User): Answer

    @POST("/forgotPassword")
    suspend fun forgotPassword(@Body email: String): Answer

    @Multipart
    @POST("/loadImageAndUsername")
    suspend fun loadImageAndUsername(
        @Part("userId") userId : RequestBody,
        @Part("username") username : RequestBody,
        @Part image: MultipartBody.Part
    ): Answer

}