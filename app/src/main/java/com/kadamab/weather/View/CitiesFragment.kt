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
import com.kadamab.weather.rm.StorageHelper
import io.realm.Realm
import kotlinx.android.synthetic.main.layout_main.view.*

class CitiesFragment : Fragment(), FavClickListener {
    private var currentWoeid = RequestParam.Default.CURRENT_CITY
    private var NAVIGATE = false
    private var init = 0
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mRealm: Realm
    private lateinit var storagehandler: StorageHelper
    private lateinit var binding : LayoutMainBinding
    var weatherViewModel: WeatherViewModel? = null
    private lateinit var viewManagerLocation: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreference.init(context)
        storagehandler = StorageHelper(context, null)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait..")
        initSetup()
    }

    private fun initSetup() {
        currentWoeid = SharedPreference.loadPreference(RequestParam.SharedPref.PREF_VAL_KEY, currentWoeid).toString()
        viewManagerLocation = LinearLayoutManager(activity)
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)

        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)    }

    private fun observeData() {
        weatherViewModel!!.observeWeatherData().observe(viewLifecycleOwner, Observer {
            if (it != null && it.main != null) {
                SharedPreference.savePreference(RequestParam.SharedPref.PREF_VAL_KEY, it.name)
                if (NAVIGATE) {
                    NAVIGATE = false
                    init = 0
                    saveData(it.name, binding.cityRecycler)
                    Navigation.findNavController(binding.spinnerCities).navigate(R.id.toDetail)
                    progressDialog?.let {
                        if (it.isShowing) it.hide()
                    }
                }

            } else {
                Toast.makeText(
                        context,
                        "Something went wrong , please try again later.",
                        Toast.LENGTH_SHORT
                ).show()
                progressDialog?.let {
                    if (it.isShowing) it.hide()
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBindings()
        observeData()
    }

    private fun setUpBindings() {
        binding.spinnerCities.setSelection(0, false)
        binding.spinnerCities?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                    ) {
                        if (++init > 1) {
                            NAVIGATE = true
                            parent?.getItemAtPosition(position)?.toString()?.let {
                                weatherViewModel!!.requestWeatherData(it)
                                progressDialog?.show()
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        setUpSearches(binding.cityRecycler)
    }

    private fun setUpSearches(cityRecycler: RecyclerView) {
        val cities = ArrayList<String>()
        val cursor = storagehandler.getAllCities()
        cursor!!.moveToFirst()
        while (cursor.moveToNext()) {
            cities.add(cursor.getString(cursor.getColumnIndex(StorageHelper.COLUMN_NAME)))
        }
        cursor.close()

        cityRecycler.setLayoutManager(LinearLayoutManager(context))
        cityRecycler.setAdapter(LocationAdapter(cities, this))
    }

    private fun saveData(cityName: String, setUpSearches: RecyclerView) {
        /* val realm: Realm = Realm.getDefaultInstance()
         val city  = City()
         city.name = cityName
         realm.beginTransaction()
         realm.copyToRealmOrUpdate(city)
         realm.commitTransaction()*/
        val city = City()
        city.name = cityName
        storagehandler.addCity(cityName)
        setUpSearches(setUpSearches)
    }

    override fun locationOnClick(pos: Int, woeid: String?) {
        woeid?.let {
            weatherViewModel!!.requestWeatherData(it)
            NAVIGATE = true
            init = 0
            progressDialog.show()
        }
    }
}

interface FavClickListener {
    fun locationOnClick(pos: Int, woeid: String?)
}


