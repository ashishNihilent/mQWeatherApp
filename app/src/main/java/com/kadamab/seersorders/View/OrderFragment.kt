package com.kadamab.seersorders.View

import Orders
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.seersorders.Adapters.OrderAdapter
import com.kadamab.seersorders.R
import com.kadamab.seersorders.ViewModel.OrderViewModel
import com.kadamab.seersorders.databinding.LayoutMainBinding

class CitiesFragment : Fragment(), FavClickListener {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var binding: LayoutMainBinding
    var viewModel: OrderViewModel? = null
    private lateinit var viewManagerOrder: RecyclerView.LayoutManager
    private lateinit var listOrders: Orders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait..")
        initSetup()
    }

    private fun initSetup() {
        viewManagerOrder = LinearLayoutManager(activity)
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)

        viewModel =
            ViewModelProvider(requireParentFragment(), factory).get(OrderViewModel::class.java)
    }

    private fun observeData() {
        viewModel!!.requestListOrderData()
        viewModel!!.observeOrdeApirData().observe(viewLifecycleOwner, Observer {
            if (it != null && it.customers != null) {
                listOrders = it
                setUpSearches(binding.orderRecycler, it)
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong , please try again later.",
                    Toast.LENGTH_SHORT
                ).show()
                progressDialog.let {
                    if (it.isShowing) it.hide()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun setUpSearches(cityRecycler: RecyclerView, orders: Orders) {
        cityRecycler.setLayoutManager(LinearLayoutManager(context))
        cityRecycler.setAdapter(OrderAdapter(context, orders, this))
    }

    override fun locationOnClick(pos: Int) {
        viewModel?.setOrder(listOrders.customers.get(pos))
        Navigation.findNavController(binding.orderRecycler).navigate(R.id.toDetail)
    }
}

interface FavClickListener {
    fun locationOnClick(pos: Int)
}


