package com.hsd.contest.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "time_seq_datas",
    foreignKeys = [ForeignKey(
        entity = Sport::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("sportId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("sportId")]
)
data class TimeSeqData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val tag: String,
    val time: Long,  // ms
    val value: Long,
    val sportId: Long
)

enum class TimeSeqDataTag {
    HEART_RATE,
    VELOCITY,
    DISTANCE,
    STEP_DELTA,
    STRIDE_FREQUENCY
}