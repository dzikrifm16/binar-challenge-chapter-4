package com.example.myapplication.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.DataClass.Category
import com.example.myapplication.Adapter.CategoryAdapter
import com.example.myapplication.DataClass.ItemMenu
import com.example.myapplication.Adapter.MenuAdapter
import com.example.myapplication.R
import com.example.myapplication.SharedPreferences
import com.example.myapplication.ViewModel.HomeViewModel
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

	private lateinit var sharedPreferences: SharedPreferences
	private lateinit var homeViewModel: HomeViewModel
	private lateinit var menuAdapter: MenuAdapter

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private var isGrid: Boolean = true
	private var layoutType = true

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		sharedPreferences = SharedPreferences(requireActivity())

		homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
		homeViewModel.isListView.value = sharedPreferences.getPrefLayout()

		menuAdapter = MenuAdapter(listMenu, homeViewModel.isListView.value ?: true)
		binding.recycleviewVertical.adapter = menuAdapter

		homeViewModel.isListView.observe(viewLifecycleOwner) {
			setupChangeLayout()
		}

			homeViewModel.menuItem.observe(viewLifecycleOwner) { 	menuItem ->
			updateRv(menuItem)
		}

		catView()
		setupRecyclerView(isGrid)

		itemClicked()
		return binding.root

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		catView()

		setupChangeLayout()

	}

	// List Kategori
	private val listKategori = arrayListOf(
		Category("Drink", R.drawable.foodiesfeed_com_strawberry_milk_splash),
		Category("Fast Food", R.drawable.foodiesfeed_com_fried_chicken_commercial),
		Category("Western", R.drawable.foodiesfeed_com_french_fries_with_ketchup_top_view),
		Category("Europe", R.drawable.foodiesfeed_com_pizza_ready_for_baking),
		Category("Asian", R.drawable.foodiesfeed_com_crispy_spicy_chicken_wings)
	)

	// List Menu
	private val listMenu = arrayListOf(
		ItemMenu("Grilled Chicken", 75000, R.drawable.foodiesfeed_com_grilled_whole_chicken),
		ItemMenu("Cheese Burger", 45000, R.drawable.foodiesfeed_com_cheeseburger),
		ItemMenu("Sushi", 20000, R.drawable.foodiesfeed_com_salmon_sushi_maki),
		ItemMenu("Spaghetti", 35000, R.drawable.foodiesfeed_com_cherry_tomatoes_spaghetti),
		ItemMenu("Dimsum", 40000, R.drawable.foodiesfeed_com_xiaolongbao_dumplings),
		ItemMenu("Chicken Satay", 30000, R.drawable.foodiesfeed_com_grilling_chicken_satay)
	)

	private fun setupRecyclerView(isLinear: Boolean) {
		layoutType = if (isLinear) {
			showListLayout()
			true
		} else {
			showGridLayout()
			false
		}

		val adapter = MenuAdapter(listMenu, gridType = layoutType, onItemClick = {
			itemClicked()
		})

		binding.recycleviewVertical.adapter = adapter

	}
	@SuppressLint("NotifyDataSetChanged")
	private fun updateRv(menuItem: ArrayList<ItemMenu>) {
		menuAdapter.reloadData(menuItem)
		menuAdapter.gridType = homeViewModel.isListView.value ?: true
		binding.recycleviewVertical.adapter?.notifyDataSetChanged()

	}
	private fun setupChangeLayout() {
		val currentLayout = homeViewModel.isListView.value ?: sharedPreferences.getPrefLayout()

		setupRecyclerView(currentLayout)
		binding.gridlist.setOnClickListener {
			isGrid = !isGrid

			setupRecyclerView(isGrid)
			val newLayoutValue = !currentLayout
			homeViewModel.isListView.value = newLayoutValue
			sharedPreferences.savePrefLayout(newLayoutValue)

			setupRecyclerView(newLayoutValue)
			itemClicked()
		}

	}

	private fun showListLayout() {
		binding.recycleviewVertical.layoutManager = LinearLayoutManager(requireActivity())
		binding.recycleviewVertical.adapter = MenuAdapter(listMenu, gridType = true)
		binding.gridlist.setImageDrawable(
			ContextCompat.getDrawable(
				requireActivity(), R.drawable.baseline_grid_view_24
			)
		)
	}

	private fun showGridLayout() {
		binding.recycleviewVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
		binding.recycleviewVertical.adapter = MenuAdapter(listMenu, gridType = false)
		binding.gridlist.setImageDrawable(
			ContextCompat.getDrawable(
				requireActivity(), R.drawable.baseline_view_list_24
			)
		)
	}

	// RecycleViewCategory
	private fun catView() {
		binding.recycleviewHorizontal.layoutManager =
			LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
		binding.recycleviewHorizontal.adapter = CategoryAdapter(listKategori)
	}

	private fun itemClicked() {
		menuAdapter =
			MenuAdapter(listMenu, homeViewModel.isListView.value ?: true) { item ->
				val bundle = bundleOf("item" to item)
				findNavController().navigate(R.id.action_homeFragment_to_detailItem, bundle)
			}
		binding.recycleviewVertical.adapter = menuAdapter
	}
}