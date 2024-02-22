package com.adrian.delivery.activities.client.payments.paypal.paymentmethod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.adrian.delivery.R
import com.adrian.delivery.activities.client.payments.paypal.form.ClientPaymentPaypalFormActivity

class ClientPaymentMethodActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    var imageViewPaypal: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_payment_method)

        toolbar = findViewById(R.id.toolbar)
        imageViewPaypal = findViewById(R.id.imageview_paypal)

        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar?.title = "Metodo de pago"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled((true))
        imageViewPaypal?.setOnClickListener { goToPaypal() }
    }
    private fun goToPaypal(){
        val i = Intent(this, ClientPaymentPaypalFormActivity::class.java)
        startActivity(i)
    }
}