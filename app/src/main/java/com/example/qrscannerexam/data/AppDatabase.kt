package com.example.qrscannerexam.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(QRData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun qrDataDao(): QRDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "QRData.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}