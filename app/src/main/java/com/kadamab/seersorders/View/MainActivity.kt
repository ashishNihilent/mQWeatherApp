package com.kadamab.seersorders.View

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kadamab.seersorders.Common.RequestParam
import com.kadamab.seersorders.R
import com.kadamab.seersorders.databinding.ActivityMainBinding


/**

 * Created by KADAMAB on 30 March 2021

 */

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindings: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater);
        setContentView(bindings.root)
        bindings.btnHelp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.btnHelp -> {
            }
        }
    }

}