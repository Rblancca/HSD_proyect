package com.hsd.contest.spain.view.sportprofile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.hsd.contest.spain.view.sportprofile.serviceshuawei.BaseSportData
import com.hsd.contest.spain.view.sportprofile.serviceshuawei.HiHealthBaseAdapter
import com.hsd.contest.spain.view.sportprofile.serviceshuawei.ISportListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val app: Application,
    private val walkingRepository: WalkingRepository
) : ViewModel(),
    ISportListener {
    private val hiHealthBaseAdapter: HiHealthBaseAdapter = HiHealthBaseAdapter(app.applicationContext, this)
    private var currentWalk: WalkingRepository.WalkingSport? = null

    val totalSteps: LiveData<String?> = walkingRepository.getLatestSport().map {
        it?.formatTotalSteps()
    }
    val strideFrequency: LiveData<String?> = walkingRepository.getLatestSport().map {
        it?.let {
            var stride: Long = 0
            val lastStepDelta = it.stepDeltaSeq.lastOrNull()
            if (lastStepDelta != null) {
                stride = lastStepDelta.value * (60 * 1000 / HiHealthBaseAdapter.SAMPLE_INTERVAL)
            }
            stride.toString()
        }
    }
    val calorie: LiveData<String?> = walkingRepository.getLatestSport().map {
        it?.formatCalorie()
    }
    override val sportType: String
        get() = TODO("Not yet implemented")

    override fun onRecvData(data: BaseSportData) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        GlobalScope.launch {
            currentWalk = walkingRepository.createWalkingSport()
        }
        hiHealthBaseAdapter.start(this)
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        GlobalScope.launch {
            currentWalk?.stop()
            hiHealthBaseAdapter.stop()
            currentWalk = null
        }
    }

    override fun onRunning() {
        TODO("Not yet implemented")
    }

    override fun onConnect() {
        // TODO("Not yet implemented")
    }

    override fun onDisconnect() {
        TODO("Not yet implemented")
    }
}