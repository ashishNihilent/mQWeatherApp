package com.kadamab.weather.View

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.weather.Adapters.LocationAdapter
import com.kadamab.weather.Common.RequestParam
import com.kadamab.weather.Common.SharedPreference
import com.kadamab.weather.R
import com.kadamab.weather.ViewModel.WeatherViewModel
import com.kadamab.weather.databinding.LayoutMainBinding
import com.kadamab.weather.rm.City
import io.realm.Realm
import kotlinx.android.synthetic.main.layout_main.view.*

class CitiesFragment : Fragment(), FavClickListener {
    private var currentWoeid = RequestParam.Default.CURRENT_CITY
    private var NAVIGATE = false
    private var init = 0
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mRealm: Realm

    var weatherViewModel: WeatherViewModel? = null
    private lateinit var weatherRecycler: RecyclerView
    private lateinit var viewAdapterWeather: RecyclerView.Adapter<*>
    private lateinit var viewManagerWeather: RecyclerView.LayoutManager

    private lateinit var locationRecycler: RecyclerView
    private lateinit var viewAdapterLocation: RecyclerView.Adapter<*>
    private lateinit var viewManagerLocation: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LayoutMainBinding.inflate(inflater)
        mRealm = Realm.getDefaultInstance()
        setUpBindings(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait..")
    }

    private fun setUpBindings(bindings: ConstraintLayout) {
        //setUpSearches(bindings.cityRecycler)
        SharedPreference.init(context)
        currentWoeid = SharedPreference.loadPreference(
            RequestParam.SharedPref.PREF_VAL_KEY,
            currentWoeid
        )
                .toString()
        viewManagerLocation = LinearLayoutManager(activity)
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)

        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)

        weatherViewModel!!.observeWeatherData().observe(viewLifecycleOwner, Observer {
            if (it != null && it.main != null) {
                SharedPreference.savePreference(RequestParam.SharedPref.PREF_VAL_KEY, it.name)
                if (NAVIGATE) {
                    NAVIGATE = false
                    init = 0
                   // saveData(it.name, bindings.cityRecycler)
                    Navigation.findNavController(bindings).navigate(R.id.toDetail)
                }
                progressDialog.hide()
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong , please try again later.",
                    Toast.LENGTH_SHORT
                ).show()
                progressDialog.hide()
            }
        })


        bindings.spinnerCities.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (++init > 1){
                    NAVIGATE = true
                    weatherViewModel!!.requestWeatherData(
                        parent.getItemAtPosition(position).toString()
                    )
                  //  progressDialog.show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


/* currentWoeid = woeid

 try {
     locationRecycler.visibility = View.GONE
     weatherRecycler.visibility = View.VISIBLE
 }catch(e: Exception){
     e.printStackTrace()
 }*/
    }

    private fun setUpSearches(cityRecycler: RecyclerView) {
        cityRecycler.setLayoutManager(LinearLayoutManager(context))
        cityRecycler.setAdapter(LocationAdapter(mRealm.allObjects(City::class.java), this))
    }

    private fun saveData(cityName: String, setUpSearches: RecyclerView) {
        mRealm.beginTransaction()
        val city: City = mRealm.createObject(City::class.java)
        city.name = cityName
        mRealm.commitTransaction()
        setUpSearches(setUpSearches)

    }

    override fun locationOnClick(pos: Int, woeid: String?) {
        TODO("Not yet implemented")
    }
}

interface FavClickListener {
    fun locationOnClick(pos: Int, woeid: String?)
}


