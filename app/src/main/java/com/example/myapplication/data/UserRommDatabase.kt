package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase(){
    abstract fun userDao() : UserDAO

    companion object {
        @Volatile
        private var instance: UserRoomDatabase? = null
        fun getDatabase(context: Context): UserRoomDatabase{
            return instance ?: synchronized(this){
                instance = Room.databaseBuilder(
                    context,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).build()
                instance!!
            }
        }
    }
}