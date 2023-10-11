package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
		val navController = navHostFragment.navController

		binding.bottomNavBar.setupWithNavController(navController)

		navController.addOnDestinationChangedListener{ _, destination, _ ->
			when (destination.id) {
				R.id.detailItem -> hideBotNav()
				R.id.confirmOrderFragment-> hideBotNav()
				else -> showBotNav()
			}
		}

	}

	private fun showBotNav() {
		binding.bottomNavBar.visibility = View.VISIBLE
	}

	private fun hideBotNav() {
		binding.bottomNavBar.visibility = View.GONE
	}

}