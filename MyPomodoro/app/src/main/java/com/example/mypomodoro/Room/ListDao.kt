package com.example.mypomodoro.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListDao {
    @Insert
    fun insert(item: Listitem)


    @Query("SELECT * FROM items")
     fun getAllItems(): List<Listitem>

    @Query("SELECT * FROM items WHERE date = :selectedDate")
    fun getItemsByDate(selectedDate: String): List<Listitem>

    @Update
    fun updateList(item: Listitem)
}
