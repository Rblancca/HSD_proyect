package com.hsd.contest.domain.entities

data class Municipality (
    val name: String
){
    override fun toString(): String {
        return name
    }
}