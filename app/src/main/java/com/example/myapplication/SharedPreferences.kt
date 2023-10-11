package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

import androidx.core.content.edit


class SharedPreferences (context:Context) {
	companion object{
		private val USER = "User SharePref"
		private val KEY = "KEY SharePref"
	}

	private val pref: SharedPreferences =
		context.getSharedPreferences(USER, 0)

	fun getPrefLayout():Boolean{
		return pref.getBoolean(KEY, true)
	}

	fun savePrefLayout(isListView: Boolean){
		pref.edit().putBoolean(KEY, isListView).apply()
	}
}
