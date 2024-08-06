package com.sabanbingul.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferances {

    companion object {
        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferances : SharedPreferences? = null

        @Volatile private var instance : CustomSharedPreferances? = null

        private val lock = Any()
        operator fun invoke (context: Context) : CustomSharedPreferances = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferances{
            sharedPreferances = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferances()
        }

    }

    fun saveTime(time : Long){
        sharedPreferances?.edit(commit = true){
            putLong(PREFERENCES_TIME, time)
        }
    }

    fun getTime() = sharedPreferances?.getLong(PREFERENCES_TIME, 0)


}