package com.example.qrscannerexam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QRData(
    var data: String
){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}