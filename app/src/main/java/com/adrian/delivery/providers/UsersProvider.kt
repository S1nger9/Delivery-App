package com.adrian.delivery.providers

import com.adrian.delivery.api.ApiRoutes
import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.models.User
import com.adrian.delivery.routes.UsersRoutes
import retrofit2.Call
class UsersProvider {

    private var usersRoutes: UsersRoutes?= null

    init{
        val api = ApiRoutes()
        usersRoutes = api.getUserRoutes()
    }

    fun register(user: User): Call<ResponseHttp>?{
        return usersRoutes?.register(user)
    }
    fun login(email: String, password: String): Call<ResponseHttp>?{
        return usersRoutes?.login(email, password)
    }
}