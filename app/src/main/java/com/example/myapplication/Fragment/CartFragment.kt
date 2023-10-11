package com.example.myapplication.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.R
import com.example.myapplication.ViewModel.CartViewModel
import com.example.myapplication.ViewModel.ViewModelFactory
import com.example.myapplication.databinding.FragmentCartBinding


class CartFragment : Fragment() {
	private lateinit var binding: FragmentCartBinding
	private lateinit var cartViewModel: CartViewModel
	private lateinit var cartAdapter: CartAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentCartBinding.inflate(inflater, container, false)
		setUpCartViewModel()

		cartAdapter = CartAdapter(cartViewModel,false)
		binding.rvCart.setHasFixedSize(true)
		binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCart.adapter = cartAdapter

		cartViewModel.allCartItems.observe(viewLifecycleOwner) {
			cartAdapter.setData(it)

			var totalprice = 0
			it.forEach { item ->
				totalprice += item.totalPrice
			}
			val priceText = "Rp. $totalprice"
			binding.resultTotalPrice.text = priceText
		}
		addToSummary()
		return binding.root
	}

	private fun setUpCartViewModel() {
		val viewModelFactory = ViewModelFactory(requireActivity().application)
		cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
	}

	private fun addToSummary() {
		binding.btnPesan.setOnClickListener {
			findNavController().navigate(
				R.id.action_cartFragment_to_confirmOrderFragment
			)
		}

	}
}