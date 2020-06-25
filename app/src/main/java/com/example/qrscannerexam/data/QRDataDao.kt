package com.example.qrscannerexam.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QRDataDao {
    @Query("SELECT * FROM QRData")
    fun getAll(): LiveData<List<QRData>>

    @Insert
    fun insert(qrs: QRData)

    @Update
    fun update(qrs: QRData)

    @Delete
    fun delete(qrs: QRData)
}