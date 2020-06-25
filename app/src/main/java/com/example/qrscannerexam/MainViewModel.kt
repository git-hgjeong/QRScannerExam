package com.example.qrscannerexam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.qrscannerexam.data.AppDatabase
import com.example.qrscannerexam.data.QRData

class MainViewModel(application: Application) : AndroidViewModel(application){
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "QRData"
    ).build()

    fun getAll(): LiveData<List<QRData>> {
        return db.qrDataDao().getAll()
    }

    suspend fun insert(qrData:QRData){
        db.qrDataDao().insert(qrData)
    }
}