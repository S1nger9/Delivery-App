package com.adrian.delivery.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import java.lang.Exception

class SharedPref(activity: Activity) {

    private var prefs: SharedPreferences?= null

    init {
        prefs = activity.getSharedPreferences("com.optic.delivery.kotlinudemy", Context.MODE_PRIVATE)
    }

    fun save(key: String, objeto: Any){

        try{
            val gson = Gson()
            val json = gson.toJson(objeto)
            with(prefs?.edit()) {
                this?.putString(key, json)
                this?.commit()
            }
        } catch (e: Exception){
            Log.d("ERROR", "Err: ${e.message}")
        }
    }

    // el signo ? es para validar que el String se diferente de nulo
    fun getData(key: String): String?{
        val data = prefs?.getString(key, "")
        return data
    }

    fun remove(key: String){
        prefs?.edit()?.remove(key)?.apply()
    }
}