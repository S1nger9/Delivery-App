package com.adrian.delivery.providers

import android.util.Log
import com.adrian.delivery.api.ApiRoutes
import com.adrian.delivery.models.Address
import com.adrian.delivery.models.Category
import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.routes.AddressRoutes
import com.adrian.delivery.routes.CategoriesRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class AddressProvider (val token: String){

    private var addressRoutes: AddressRoutes? = null

    init{
        val api = ApiRoutes()
        addressRoutes = api.getAddressRoutes(token)
    }

    fun getAddress(idUser: String): Call<ArrayList<Address>>? {
        return addressRoutes?.getAddress(idUser, token)
    }

    fun create(address: Address): Call<ResponseHttp>? {
      return addressRoutes?.create(address, token)
    }
}
