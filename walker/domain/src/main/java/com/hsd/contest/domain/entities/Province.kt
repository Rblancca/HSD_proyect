package com.hsd.contest.domain.entities

data class Province(
    val code: String,
    val name: String
){
    override fun toString(): String {
        return name
    }
}