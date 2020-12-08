package com.hsd.contest.spain.view.sportprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hsd.contest.data.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class WalkingRepository(
    private val sportDatabase: SportDatabase,
) {
    fun getLatestSport(): LiveData<WalkingSport?> {
        return sportDatabase.getLatestSportByType(SportType.WALKING).map {
            it?.let {
                WalkingSport().apply {
                    sport = it.sport
                    it.timeSeqDatas.forEach { seqData ->
                        when (seqData.tag) {
                            // TimeSeqDataTag.STEP_DELTA -> stepDeltaSeq.add(seqData)
                            //TimeSeqDataTag.STRIDE_FREQUENCY -> strideFrequencySeq.add(seqData)
                            else -> throw IllegalStateException()
                        }
                    }
                }
            }
        }
    }


    suspend fun createWalkingSport(): WalkingSport {
        return withContext(Dispatchers.IO) {
            val sport = Sport(
                sportType = SportType.WALKING.name,
                sportStatus = SportStatus.STARTED.name
                // startTime = Calendar.getInstance()
            )
            sport.id = sportDatabase.insertSport(sport)
            WalkingSport(sport = sport)
        }
    }

    inner class WalkingSport(
        var sport: Sport = Sport(),
        val stepDeltaSeq: MutableList<TimeSeqData> = mutableListOf(),
        val strideFrequencySeq: MutableList<TimeSeqData> = mutableListOf(),
        val mutex: Mutex = Mutex()
    ) {

        override fun equals(other: Any?): Boolean {
            other as WalkingSport
            return sport == other.sport &&
                    stepDeltaSeq == other.stepDeltaSeq
        }

        fun formatTotalSteps(): String {
            var total: Long = 0
            stepDeltaSeq.forEach {
                total += it.value
            }
            return total.toString()
        }

        fun formatCalorie(): String {
            // calorie(kcal) = weight(kg) * distance(km) * 1.036
            return String.format("%.2f", 60 * formatTotalSteps().toLong() * 0.4 * 0.001 * 1.036)
        }

        suspend fun stop() {
            withContext(Dispatchers.IO) {
                mutex.withLock {
                    // sport.stopTime = Calendar.getInstance()
                    //sport.sportStatus = SportStatus.STOPPED
                    sportDatabase.updateSport(sport)
                }
            }
        }
    }
}