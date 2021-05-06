package com.kadamab.seersorders.Repository

import Orders
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kadamab.seersorders.Retrofit.InterOrder
import com.kadamab.seersorders.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**

*Created by KADAMAB on 6 May 2021

*/
class OrderDataRepository : ViewModel() {

    private val orderModel: MutableLiveData<Orders> = MutableLiveData()

    fun observeWeatherData(): MutableLiveData<Orders> {
        return orderModel
    }

    fun requestOrderData() {

        val weatherDataCall: Call<Orders> = myInterface.getOrder()
        weatherDataCall.enqueue(object : Callback<Orders?> {
            override fun onResponse(call: Call<Orders?>, response: Response<Orders?>) {
                orderModel.setValue(response.body())
            }

            override fun onFailure(call: Call<Orders?>, t: Throwable) {
                Log.i("ERRRRR",t.localizedMessage)
                orderModel.postValue(null)
            }
        })

    }

    companion object {
        private lateinit var myInterface: InterOrder
        private var orderDataRepository: OrderDataRepository? = null
        val instance: OrderDataRepository?
            get() {
                if (orderDataRepository == null) {
                    orderDataRepository = OrderDataRepository()
                }
                return orderDataRepository
            }
    }

    init {
        myInterface = ServiceBuilder.buildService(InterOrder::class.java)
    }
}
