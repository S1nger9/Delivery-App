package com.adrian.delivery.providers

import android.util.Log
import com.adrian.delivery.api.ApiRoutes
import com.adrian.delivery.models.Category
import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.routes.CategoriesRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class CategoriesProvider (val token: String){

    private var categoriesRoutes: CategoriesRoutes? = null

    init{
        val api = ApiRoutes()
        categoriesRoutes = api.getCategoriesRoutes(token)
    }

    fun getAll(): Call<ArrayList<Category>>? {
        return categoriesRoutes?.getAll(token)
    }

    fun create(file: File, category: Category): Call<ResponseHttp>? {
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        val image = MultipartBody.Part.createFormData("image",file.name, reqFile)

        Log.d("CATEGORY", category.toJson())

        val parts = arrayOf<MultipartBody.Part?>(image)

        val requestBody = RequestBody.create(MediaType.parse("text/plain"), category.toJson())
        return categoriesRoutes?.create(parts, requestBody, token)
    }
}
