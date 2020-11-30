package com.hsd.contest.domain.entities

data class Municipality (
    val name: String,
    val code : String
){
    override fun toString(): String {
        return name
    }
}