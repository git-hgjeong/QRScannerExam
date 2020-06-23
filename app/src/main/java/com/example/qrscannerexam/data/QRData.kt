package com.example.qrscannerexam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QRData(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "qr_data") val qrData: String?
)