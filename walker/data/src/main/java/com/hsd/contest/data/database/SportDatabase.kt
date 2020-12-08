package com.hsd.contest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface SportDatabase {

    fun getLatestSportByType(sportType: SportType): LiveData<SportAndTimeSeqData?>

    fun getSports(sportType: SportType): LiveData<List<SportAndTimeSeqData>>

    fun getSport(id: Long): LiveData<SportAndTimeSeqData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSport(sport: Sport) : Long

    fun updateSport(sport: Sport)

}