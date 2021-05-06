package com.kadamab.seersorders.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kadamab.seersorders.ViewModel.OrderViewModel
import com.kadamab.seersorders.databinding.LayoutDetailBinding
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.layout_detail.view.*

class OrderDetailFragment : Fragment() {
    private var orderViewModel: OrderViewModel? = null
    private lateinit var binding: LayoutDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutDetailBinding.inflate(inflater)
        getData(binding.root)
        return binding.root
    }

    private fun getData(root: ConstraintLayout) {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        orderViewModel =
            ViewModelProvider(requireParentFragment(), factory).get(OrderViewModel::class.java)
        orderViewModel!!.observeOrderData().observe(viewLifecycleOwner, Observer {
            binding.rootLayout.orderLayout.txtAddValue.text = it.customer.address
            binding.rootLayout.orderLayout.txtCityValue.text = it.customer.city
            binding.rootLayout.orderLayout.txtZip.text = it.customer.state
            binding.rootLayout.orderLayout.txtPhone.text = it.customer.phoneNumber.toString()
            binding.rootLayout.orderLayout.tvName.text =
                "${it.customer.firstName} ${it.customer.lastName}"
            binding.rootLayout.orderLayout.textDate.text = "Schedule At : ${it.scheduleDate}"


        })
    }
}


