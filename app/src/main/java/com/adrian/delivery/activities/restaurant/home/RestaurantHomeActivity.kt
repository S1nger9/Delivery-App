package com.adrian.delivery.activities.restaurant.home


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.adrian.delivery.R
import com.adrian.delivery.activities.MainActivity
import com.adrian.delivery.fragments.client.ClientProfileFragment
import com.adrian.delivery.fragments.restaurant.RestaurantCategoryFragment
import com.adrian.delivery.fragments.restaurant.RestaurantOrdersFragment
import com.adrian.delivery.fragments.restaurant.RestaurantProductFragment
import com.adrian.delivery.models.User
import com.adrian.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

    class RestaurantHomeActivity : AppCompatActivity() {

    private val TAG = "RestaurantHomeActivity"
    //var buttonLogout: Button?= null
    var sharedPref: SharedPref? = null

    var bottomNavigation:BottomNavigationView? = null
    // Método onCreate que se llama al crear la actividad.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establece el layout para esta actividad.
        setContentView(R.layout.activity_restaurant_home)

        sharedPref = SharedPref(this)

        // Encuentra el botón de cierre de sesión en el layout.
        //buttonLogout = findViewById(R.id.btn_logout)
        // Establece un listener en el botón de cierre de sesión.
        //buttonLogout?.setOnClickListener { logout()  }
        openFragment(RestaurantOrdersFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {


            when (it.itemId) {

                R.id.item_home -> {
                    openFragment(RestaurantOrdersFragment())
                    true
                }

                R.id.item_category -> {
                    openFragment(RestaurantCategoryFragment())
                    true
                }

                R.id.item_product -> {
                    openFragment(RestaurantProductFragment())
                    true
                }

                R.id.item_profile -> {
                    openFragment(ClientProfileFragment())
                    true
                }
                else -> false


            }

        }
        // Obtiene el usuario de la sesión.
        getUserFromSession()
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    // Método para manejar el cierre de sesión.
    private fun logout (){
        sharedPref?.remove("user")
        val i = Intent(this, MainActivity::class.java)

        // Inicia la actividad MainActivity.
        startActivity(i)
    }

    // Método para obtener la información del usuario de las preferencias compartidas.
    private fun getUserFromSession(){
        // Crea una instancia de Gson para convertir objetos Kotlin a JSON y viceversa.
        val gson = Gson()

        // Verifica si existe alguna información guardada bajo la clave "user" en las
        // preferencias compartidas. Las preferencias compartidas son un mecanismo para
        // almacenar datos de manera ligera en Android
        if (!sharedPref?.getData("user").isNullOrBlank()){
            // SI EL USUARIO EXISTE EN SESION
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)

            // Registra la información del usuario para depuración.(La depuración, es el proceso de identificar y corregir errores del programa.)
            Log.d(TAG, "Usuario: $user")
        }
    }
}