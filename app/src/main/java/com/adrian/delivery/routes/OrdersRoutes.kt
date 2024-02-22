package com.adrian.delivery.routes

import com.adrian.delivery.models.Address
import com.adrian.delivery.models.Category
import com.adrian.delivery.models.Order
import com.adrian.delivery.models.ResponseHttp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface OrdersRoutes {
    @GET("orders/findByStatus/{status}")
    fun getOrdersByStatus(
        @Path("status") status: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Order>>

    @GET("orders/findByClientAndStatus/{id_client}/{status}")
    fun getOrdersByClientAndStatus(
        @Path("id_client") idClient: String,
        @Path("status") status: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Order>>

    @GET("orders/findByDeliveryAndStatus/{id_delivery}/{status}")
    fun getOrdersByDeliveryAndStatus(
        @Path("id_delivery") idDelivery: String,
        @Path("status") status: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Order>>

    @POST("orders/create")
    fun create(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

    @PUT("orders/updateToDispatched")
    fun updateToDispatched(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

    @PUT("orders/updateToOnTheWay")
    fun updateToOnTheWay(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

    @PUT("orders/updateToDelivered")
    fun updateToDelivered(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

    @PUT("orders/updateLatLng")
    fun updateLatLng(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

}