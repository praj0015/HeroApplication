package com.example.heroapplication.database

import android.content.Context
import androidx.room.*
import com.example.heroapplication.model.HeroModel

@Database(entities = [HeroModel::class], version = 1, exportSchema = false)
abstract class HeroDatabase : RoomDatabase() {

    abstract fun heroDao() : HeroDao

    companion object {
        @Volatile private var INSTANCE : HeroDatabase? = null

        fun getDatabase(context: Context) : HeroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HeroDatabase::class.java,
                    "HeroTable"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}