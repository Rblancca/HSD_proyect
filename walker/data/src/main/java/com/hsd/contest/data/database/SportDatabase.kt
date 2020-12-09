package com.hsd.contest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface SportDatabase {

    fun getLatestSportByType(sportType: SportType): LiveData<SportAndTimeSeqData?>

    fun getSports(sportType: SportType): LiveData<List<SportAndTimeSeqData>>

    fun getSport(id: Long): LiveData<SportAndTimeSeqData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSport(sport: Sport): Long

    fun updateSport(sport: Sport)

    fun getTimeSeqDatas(): LiveData<List<TimeSeqData>>

    fun getTimeSeqData(id: Long): LiveData<TimeSeqData>

    fun getTimeSeqDataForSport(sportId: Long): LiveData<List<TimeSeqData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimeSeqData(data: TimeSeqData): Long
}