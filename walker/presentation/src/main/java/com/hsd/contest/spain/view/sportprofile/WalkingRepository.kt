package com.hsd.contest.spain.view.sportprofile

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import androidx.lifecycle.map
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

class WalkingRepository(
    private val sportDao: SportDao,
) {
    fun getLatestSport(): LiveData<WalkingSport?> {
        return sportDao.getLatestSportByType(SportType.WALKING).map {
            it?.let {
                WalkingSport().apply {
                    sport = it.sport
                    it.timeSeqDatas.forEach { seqData ->
                        when (seqData.tag) {
                            TimeSeqDataTag.STEP_DELTA -> stepDeltaSeq.add(seqData)
                            TimeSeqDataTag.STRIDE_FREQUENCY -> strideFrequencySeq.add(seqData)
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
                sportType = SportType.WALKING,
                sportStatus = SportStatus.STARTED,
                startTime = Calendar.getInstance()
            )
            sport.id = sportDao.insertSport(sport)
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


        suspend fun stop() {
            withContext(Dispatchers.IO) {
                mutex.withLock {
                    sport.stopTime = Calendar.getInstance()
                    sport.sportStatus = SportStatus.STOPPED
                    sportDao.updateSport(sport)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var instance: WalkingRepository? = null

        fun getInstance(sportDao: SportDao, timeSeqDataDao: TimeSeqDataDao) =
            instance ?: synchronized(this) {
                //instance ?: WalkingRepository(sportDao, timeSeqDataDao).also { instance = it }
            }
    }
}