package com.hsd.contest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Sport::class, TimeSeqData::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sportDao(): SportDao
    abstract fun timeSeqDataDao(): TimeSeqDataDao
}