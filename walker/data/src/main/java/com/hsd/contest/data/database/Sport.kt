package com.hsd.contest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports")
data class Sport(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val sportType: String = SportType.INDOOR_RUNNING.name,
    var sportStatus: String = SportStatus.STARTED.name,
    var duration: Long = 0
)
