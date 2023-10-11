package com.example.myapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class  CartDatabase: RoomDatabase() {

    abstract val cartDao: CartDao

    companion object {

        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        CartDatabase::class.java,"cart_database"
                    )
                        .build()
                    INSTANCE = instance
                    }
                return instance
                }
            }
        }
    }
