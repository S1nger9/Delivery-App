package com.adrian.delivery.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.adrian.delivery.R
import com.adrian.delivery.activities.client.home.ClientHomeActivity
import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.models.User
import com.adrian.delivery.providers.UsersProvider
import com.adrian.delivery.utils.SharedPref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"

    var imageViewGoToLogin: ImageView? = null
    var editTextName: EditText? = null
    var editTextLastName: EditText? = null
    var editTextEmail: EditText? = null
    var editTextPhone: EditText? = null
    var editTextPassword: EditText? = null
    var editTextConfirmPassword: EditText? = null
    var buttonRegister: Button? = null

    var usersProvider = UsersProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageViewGoToLogin = findViewById(R.id.imageview_go_to_login)
        editTextName = findViewById(R.id.edittext_name)
        editTextLastName = findViewById(R.id.edittext_lastname)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPhone= findViewById(R.id.edittext_phone)
        editTextPassword= findViewById(R.id.edittext_password)
        editTextConfirmPassword= findViewById(R.id.edittext_confirm_password)
        editTextConfirmPassword= findViewById(R.id.edittext_confirm_password)
        buttonRegister= findViewById(R.id.btn_register)


        imageViewGoToLogin?.setOnClickListener{ goToLogin()}
        buttonRegister?.setOnClickListener{ register()}
    }

    private fun register(){
        val name = editTextName?.text.toString()
        val lastname = editTextLastName?.text.toString()
        val email = editTextEmail?.text.toString()
        val phone = editTextPhone?.text.toString()
        val password = editTextPassword?.text.toString()
        val confirmPassword = editTextConfirmPassword?.text.toString()

        if(isValidForm(name=name, lastname=lastname, email=email, phone=phone, password=password, confirmPassword=confirmPassword)){
           val user = User(
               name=name,
               lastname=lastname,
               email=email,
               phone=phone,
               password=password
           )
            usersProvider.register(user)?.enqueue(object: Callback<ResponseHttp>{
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {

                    if(response.body()?.success ==true){
                        saveUserInSession(response.body()?.data.toString())
                        goToClientHome()
                    }

                    Toast.makeText(this@RegisterActivity,response.body()?.message,Toast.LENGTH_LONG).show()

                    Log.d(TAG,"Response: ${response}")
                    Log.d(TAG,"Body: ${response.body()}")

                }

                override fun onFailure(p0: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Se produjo un error ${t.message}")
                    Toast.makeText(this@RegisterActivity,"Se produjo un error ${t.message}",Toast.LENGTH_LONG).show()
                }

            })
        }


    }

    private fun goToClientHome(){
        val i = Intent(this, SaveImageActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar el historial de pantallas
        startActivity(i)
    }
    private fun saveUserInSession(data: String){

        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref.save("user",user)
    }

    fun String.isEmailValid():Boolean{
        //Esta linea de código es para comprobar si se ha insertado un formato válido de email
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isValidForm(
        name: String,
        lastname: String,
        email: String,
        phone:String,
        password:String,
        confirmPassword:String): Boolean {

        if(name.isBlank()){
            Toast.makeText(this,"Debes ingresar el nombre", Toast.LENGTH_SHORT).show()
            return false
        }

        if(lastname.isBlank()){
            Toast.makeText(this,"Debes ingresar el apellido", Toast.LENGTH_SHORT).show()

            return false
        }

        if(phone.isBlank()){
            Toast.makeText(this,"Debes ingresar el telefono", Toast.LENGTH_SHORT).show()

            return false
        }

        if(email.isBlank()){
            Toast.makeText(this,"Debes ingresar el email", Toast.LENGTH_SHORT).show()

            return false
        }

        if(password.isBlank()){
            Toast.makeText(this,"Debes ingresar la contraseña", Toast.LENGTH_SHORT).show()

            return false
        }

        if(confirmPassword.isBlank()){
            Toast.makeText(this,"Debes ingresar la confirmacion", Toast.LENGTH_SHORT).show()

            return false
        }

        if(!email.isEmailValid()){
            Toast.makeText(this,"El email no es valido", Toast.LENGTH_SHORT).show()

            return false
        }

        if (password!=confirmPassword){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}