package com.adrian.delivery.activities.client.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.adrian.delivery.R
import com.adrian.delivery.activities.MainActivity
import com.adrian.delivery.fragments.client.ClientCategoriesFragment
import com.adrian.delivery.fragments.client.ClientOrdersFragment
import com.adrian.delivery.fragments.client.ClientProfileFragment
import com.adrian.delivery.models.User
import com.adrian.delivery.providers.UsersProvider
import com.adrian.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class ClientHomeActivity : AppCompatActivity() {

    private val TAG = "ClientHomeActivity"
    //var buttonLogout: Button?= null
    var sharedPref: SharedPref? = null

    var bottomNavigation:BottomNavigationView? = null
    var usersProvider: UsersProvider? = null
    var user: User? =null
    // Método onCreate que se llama al crear la actividad.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establece el layout para esta actividad.
        setContentView(R.layout.activity_client_home)

        sharedPref = SharedPref(this)

        // Encuentra el botón de cierre de sesión en el layout.
        //buttonLogout = findViewById(R.id.btn_logout)

        // Establece un listener en el botón de cierre de sesión.

        //buttonLogout?.setOnClickListener { logout()  }

        openFragment(ClientCategoriesFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {


            when (it.itemId) {

                R.id.item_home -> {
                    openFragment(ClientCategoriesFragment())
                    true
                }

                R.id.item_orders -> {
                    openFragment(ClientOrdersFragment())
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

        usersProvider = UsersProvider(token = user?.sessionToken!!)
        createToken()
    }

    private fun createToken(){
        usersProvider?.createToken(user!!, this)
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    // Método para manejar el cierre de sesión.


    // Método para obtener la información del usuario de las preferencias compartidas.
    private fun getUserFromSession(){
        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }
}

