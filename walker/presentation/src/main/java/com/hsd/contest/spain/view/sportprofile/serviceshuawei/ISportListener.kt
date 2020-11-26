package com.hsd.contest.spain.view.sportprofile.serviceshuawei

interface ISportListener : ISportAction, IConnection {
    val sportType: String
    fun onRecvData(data: BaseSportData)
}