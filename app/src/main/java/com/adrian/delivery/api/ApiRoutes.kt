package com.adrian.delivery.api

import com.adrian.delivery.routes.AddressRoutes
import com.adrian.delivery.routes.CategoriesRoutes
import com.adrian.delivery.routes.OrdersRoutes
import com.adrian.delivery.routes.ProductsRoutes
import com.adrian.delivery.routes.UsersRoutes
import retrofit2.create

class ApiRoutes {

    val API_URL = "http://192.168.56.1:3000/api/"
    val retrofit = RetrofitClient()

    fun getUserRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

    fun getUserRoutesWithToken(token:String): UsersRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(UsersRoutes::class.java)
    }

    fun getCategoriesRoutes(token:String): CategoriesRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(CategoriesRoutes::class.java)
    }

    fun getAddressRoutes(token:String): AddressRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(AddressRoutes::class.java)
    }

    fun getOrdersRoutes(token:String): OrdersRoutes {
            return retrofit.getClientWithToken(API_URL, token).create(OrdersRoutes::class.java)
        }

    fun getProductsRoutes(token:String): ProductsRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(ProductsRoutes::class.java)
    }
}



