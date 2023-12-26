package com.adrian.delivery.routes

import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.models.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface UsersRoutes {

    @POST("users/create")
    fun register(@Body user: User): Call<ResponseHttp>

}