package com.example.heroapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.heroapplication.model.HeroModel

@Dao
interface HeroDao {
    @Insert
    fun insertAll(vararg heroes: HeroModel) :List<Long>

    @Query("SELECT * FROM HeroTable")
    fun getAllHeroes() : List<HeroModel>

    @Query("DELETE FROM HeroTable")
    fun deleteAll()

    @Query("SELECT * FROM HeroTable WHERE id = :heroId")
    fun getHero(heroId: Int) : HeroModel
}