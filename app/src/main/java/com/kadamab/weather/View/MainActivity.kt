package com.kadamab.weather.View

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kadamab.weather.Common.RequestParam
import com.kadamab.weather.R
import com.kadamab.weather.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


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
                launchHelp()
            }
        }
    }

    private fun launchHelp() {
        val url = RequestParam.Default.HELP_URL
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}