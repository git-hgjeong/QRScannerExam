package com.example.qrscannerexam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QRDataDao {
    @Query("SELECT * FROM QRData")
    fun getAll(): List<QRData>

    @Insert
    fun insertAll(vararg qrs: QRData)
}