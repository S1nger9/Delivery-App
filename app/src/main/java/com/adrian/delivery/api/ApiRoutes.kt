package com.adrian.delivery.api

import com.adrian.delivery.routes.UsersRoutes
import retrofit2.create

class ApiRoutes {

    val API_URL = "http://192.168.56.1:3000/api/"
    val retrofit = RetrofitClient()

    fun getUserRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }
}