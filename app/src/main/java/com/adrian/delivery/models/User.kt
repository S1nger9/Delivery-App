package com.adrian.delivery.models

import com.google.gson.annotations.SerializedName

class User(
   @SerializedName("id") val id: String,
   @SerializedName("name") val name: String,
   @SerializedName("lastname") val lastname: String,
   @SerializedName("email") val email: String,
   @SerializedName("phone") val phone: String,
   @SerializedName("password") val password: String,
   @SerializedName("image") val image: String,
   @SerializedName("session_token") val sessionToken: String,
   @SerializedName("is_available") val isAvailable: Boolean,
   ) {
    override fun toString(): String {
        return "User(id='$id', name='$name', lastname='$lastname', email='$email', phone='$phone', password='$password', image='$image', sessionToken='$sessionToken', isAvailable=$isAvailable)"
    }
}
