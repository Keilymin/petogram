package com.burlakov.petogram.services

import com.burlakov.petogram.model.User
import retrofit2.http.GET

interface SingUpService {
    @GET("/")
    suspend fun singUp(): User
}