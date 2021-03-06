package com.hsd.contest.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SportDao {
    @Transaction
    @Query("select * FROM sports WHERE sportType = :sportType ORDER BY id DESC LIMIT 1")
    fun getLatestSportByType(sportType: String): LiveData<SportAndTimeSeqData?>

    @Transaction
    @Query("select * FROM sports WHERE sportType = :sportType")
    fun getSports(sportType: String): LiveData<List<SportAndTimeSeqData>>


    @Transaction
    @Query("select * FROM sports WHERE id = :id")
    fun getSport(id: Long): LiveData<SportAndTimeSeqData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSport(sport: Sport): Long

    @Update
    fun updateSport(sport: Sport)
}