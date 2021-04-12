package com.kadamab.weather.View

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kadamab.weather.databinding.ActivityMainBinding

/**

 * Created by KADAMAB on 30 March 2021

 */

class MainActivity : AppCompatActivity(){


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindings = ActivityMainBinding.inflate(layoutInflater);
        setContentView(bindings.root)
    }
}