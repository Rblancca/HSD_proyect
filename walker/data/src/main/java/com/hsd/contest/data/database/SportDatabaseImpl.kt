package com.hsd.contest.data.database

import androidx.lifecycle.LiveData

class SportDatabaseImpl(private val appDatabase: AppDatabase) : SportDatabase {
      override fun getLatestSportByType(sportType: SportType): LiveData<SportAndTimeSeqData?> =
         appDatabase.sportDao().getLatestSportByType(sportType.name)


    override fun getSports(sportType: SportType): LiveData<List<SportAndTimeSeqData>> =
         appDatabase.sportDao().getSports(sportType.name)


     override fun getSport(id: Long): LiveData<SportAndTimeSeqData?> =
         appDatabase.sportDao().getSport(id)

     override fun insertSport(sport: Sport): Long =
         appDatabase.sportDao().insertSport(sport)


     override fun updateSport(sport: Sport) {
         appDatabase.sportDao().updateSport(sport)
     }
}