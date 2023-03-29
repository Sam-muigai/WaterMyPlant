package com.sam.watermyplant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(
    @PrimaryKey
    val id:Int? = null,
    val plantName:String = "",
    val imageUri:String = "",
    val date:String = "",
    val time:String = ""
)
