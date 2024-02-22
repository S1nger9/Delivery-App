package com.adrian.delivery.providers

import android.app.Activity
import android.util.Log
import com.adrian.delivery.api.ApiRoutes
import com.adrian.delivery.models.Category
import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.models.User
import com.adrian.delivery.routes.UsersRoutes
import com.adrian.delivery.utils.SharedPref
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class UsersProvider (val token: String? = null){

    val TAG = "UsersProvider"
    private var usersRoutes: UsersRoutes?= null
    private var usersRoutesToken: UsersRoutes?= null

    init{
        val api = ApiRoutes()
        usersRoutes = api.getUserRoutes()

        if (token != null){
            usersRoutesToken = api.getUserRoutesWithToken(token!!)
        }

    }

    fun createToken(user:User, context: Activity){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result

            val sharedPref = SharedPref(context)

            user.notificationToken = token

            sharedPref.save("user", user)

            // Get new FCM registration token
            Log.d(TAG, "TOKEN DE NOTIFICACIONES $token")
        })
    }
    fun getDeliveryMen(): Call<ArrayList<User>>? {
        return usersRoutesToken?.getDeliveryMen(token!!)
    }
    fun register(user: User): Call<ResponseHttp>?{
        return usersRoutes?.register(user)
    }
    fun login(email: String, password: String): Call<ResponseHttp>?{
        return usersRoutes?.login(email, password)
    }

    fun updateWithoutImage(user: User): Call<ResponseHttp>?{
        return usersRoutesToken?.updateWithoutImage(user, token!!)
    }

    fun update(file: File, user:User): Call<ResponseHttp>? {
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        val image = MultipartBody.Part.createFormData("image",file.name, reqFile)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), user.toJson())
        return usersRoutesToken?.update(image, requestBody, token!!)
    }
}