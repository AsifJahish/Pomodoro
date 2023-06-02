package com.example.mypomodoro.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Listitem(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var date: String? = null,
    var description: String? = null,
    var condition: String?=null
)
