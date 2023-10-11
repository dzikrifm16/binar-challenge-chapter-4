package com.example.myapplication.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.ViewModel.CartViewModel
import com.example.myapplication.ViewModel.ViewModelFactory
import com.example.myapplication.databinding.FragmentConfirmOrderBinding

class ConfirmOrderFragment : Fragment() {
	private lateinit var binding: FragmentConfirmOrderBinding
	private lateinit var cartViewModel: CartViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentConfirmOrderBinding.inflate(inflater,container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		btnBack()
		setUpCartViewModel()
		showRecyclerView()
		summary()
	}

	private fun summary() {
		cartViewModel.allCartItems.observe(viewLifecycleOwner) {
			var listMenu = ""
			var priceMenu = ""
			var totalPrice = 0
			it.forEach { item ->
				listMenu += "${item.itemName} - ${item.itemQuantity} x ${item.priceMenu}\n"
				priceMenu += "Rp. ${item.totalPrice}\n"
				totalPrice += item.totalPrice
			}

			val totalText = "Rp. $totalPrice"
			binding.itemName.text = listMenu
			binding.itemQuantity.text = priceMenu
			binding.totalPrice.text = totalText
		}
	}

	private fun setUpCartViewModel() {
		val viewModelFactory = ViewModelFactory(requireActivity().application)
		cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
	}

	private fun showRecyclerView() {
		val adapter = CartAdapter(cartViewModel,true)

		binding.rvConfirm.adapter = adapter
		binding.rvConfirm.layoutManager = LinearLayoutManager(requireContext())

		cartViewModel.allCartItems.observe(viewLifecycleOwner) {
			adapter.setData(it)

			var totalPrice = 0
			it.forEach { item ->
				totalPrice += item.totalPrice
			}
		}
	}

	private fun btnBack() {
		binding.btnback.setOnClickListener {
			requireActivity().onBackPressed()
		}
	}
}