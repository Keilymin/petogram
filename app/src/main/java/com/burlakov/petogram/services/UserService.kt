package com.burlakov.petogram.services

import com.burlakov.petogram.model.Answer
import com.burlakov.petogram.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("/singUp")
    suspend fun singUp(@Body user: User): Answer

    @POST("/logIn")
    suspend fun logIn(@Body user: User): Answer
}