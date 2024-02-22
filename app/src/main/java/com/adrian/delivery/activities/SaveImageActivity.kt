package com.adrian.delivery.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.adrian.delivery.R
import com.adrian.delivery.activities.client.home.ClientHomeActivity
import com.adrian.delivery.models.ResponseHttp
import com.adrian.delivery.models.User
import com.adrian.delivery.providers.UsersProvider
import com.adrian.delivery.utils.SharedPref
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.selects.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File



class SaveImageActivity : AppCompatActivity() {

    var TAG = "SaveImageActivity"

    var circleImageUser: CircleImageView?=null
    var buttonNext: Button?=null
    var buttonConfirm: Button?=null

    private var imageFile: File?= null

    var userProvider:UsersProvider? = null
    var user: User? = null
    var sharedPref: SharedPref?= null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_image)

        sharedPref = SharedPref(this)

        getUserFromSession()

        userProvider = UsersProvider(user?.sessionToken)

        circleImageUser = findViewById(R.id.circleimage_user)
        buttonNext = findViewById(R.id.btn_next)
        buttonConfirm = findViewById(R.id.btn_confirm)
        
        circleImageUser?.setOnClickListener{ selectImage() }

        buttonNext?.setOnClickListener { goToClientHome() }
        buttonConfirm?.setOnClickListener { saveImage() }
    }

    private fun saveImage(){
        if(imageFile != null && user != null){
            userProvider?.update(imageFile!!, user!!)?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {

                    Log.d(TAG,"RESPONSE: $response")
                    Log.d(TAG,"BODY: ${response.body()}")

                    saveUserInSession(response.body()?.data.toString())

                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Error: ${t.message}")
                    Toast.makeText(this@SaveImageActivity,"Error: ${t.message}",Toast.LENGTH_LONG).show()
                }

            })
        }
        else{
            Toast.makeText(this, "La imagen no puede ser nula ni tampoco los datos de sesion del usuario", Toast.LENGTH_LONG).show()
        }

    }
    private fun saveUserInSession(data: String){
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref?.save("user",user)
        goToClientHome()
    }
    private fun goToClientHome(){
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar el historial de pantallas
        startActivity(i)
    }

    private fun getUserFromSession(){

        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            // SI EL USUARIO EXISTE EN SESION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private val startImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult ()){ result:ActivityResult ->

            val resultCode = result.resultCode
            val data = result.data

            if(resultCode == Activity.RESULT_OK){
                val fileURI = data?.data
                imageFile = File(fileURI?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                circleImageUser?.setImageURI(fileURI)
            }
            else if (resultCode == ImagePicker.RESULT_ERROR){
                Toast.makeText(this,ImagePicker.getError(data),Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Tarea se cancelo",Toast.LENGTH_LONG).show()
            }

    }

    private fun selectImage(){
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
        
    }
}