package com.hsd.contest.data.response

interface Mappable<out Domain> {
    fun toDomain(): Domain
}