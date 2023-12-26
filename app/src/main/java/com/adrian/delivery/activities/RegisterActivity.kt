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

    @SuppressLint("MissingInflatedId")
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
            Toast.makeText(this, "El formulario es valido", Toast.LENGTH_SHORT).show()
        }

        Log.d(TAG, "El nombre es: $name")
        Log.d(TAG, "El apellido es: $lastname")
        Log.d(TAG, "El email es: $email")
        Log.d(TAG, "El phone es: $phone")
        Log.d(TAG, "El password es: $password")
        Log.d(TAG, "El confirmPassword es: $confirmPassword")
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