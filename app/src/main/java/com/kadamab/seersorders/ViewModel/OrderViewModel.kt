package com.kadamab.seersorders.ViewModel

import Customers
import Orders
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kadamab.seersorders.Repository.OrderDataRepository

/**

 *Created by KADAMAB on 31 March 2021

 */
class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var customers : Customers
    val orderData : MutableLiveData<Customers> = MutableLiveData()

    private val orderDataRepository: OrderDataRepository = OrderDataRepository.instance!!

    fun requestListOrderData() {
        orderDataRepository.requestOrderData()
    }

    fun observeOrdeApirData(): MutableLiveData<Orders> {
        return orderDataRepository.observeWeatherData()
    }

    fun setOrder(customers: Customers){
        orderData.postValue(customers)
    }

    fun observeOrderData(): MutableLiveData<Customers> {
        return orderData
    }
}
