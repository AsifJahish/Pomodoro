package com.example.mypomodoro.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Listitem::class], version = 1)
abstract class DB:  RoomDatabase() {
    abstract fun itemDao(): ListDao
    companion object {
        private var INSTANCE: DB? = null

        fun getDatabase(context: Context): DB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "to_do"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}