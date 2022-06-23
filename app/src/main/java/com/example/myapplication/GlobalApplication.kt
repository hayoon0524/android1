package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.UserRoomDatabase

class GlobalApplication : Application(){

    companion object{
        private var instance: GlobalApplication? = null
        fun getInstance(): GlobalApplication
        = instance?: GlobalApplication().also {instance = it}

        private lateinit var sharedPref: SharedPreferences
        private lateinit var editor : SharedPreferences.Editor
        lateinit var db : UserRoomDatabase
        lateinit var userRepository: UserRepository
    }
    override fun onCreate() {
        super.onCreate()

        sharedPref = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        db = UserRoomDatabase.getDatabase(this)
        userRepository = UserRepository(db.userDao())
    }

    fun putKeyValue(key: String, value: Boolean){
        editor.putBoolean(key,value)
        editor.apply()
    }

    fun getValue(key: String): Boolean = sharedPref.getBoolean(key, false)
}