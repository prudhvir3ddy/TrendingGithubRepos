package com.prudhvir3ddy.trendinggithubrepos.model

data class NetworkResponse(
    val author: String,
    val name: String,
    val avatar: String,
    val url: String,
    val description: String,
    val language: String?,
    val languageColor: String?,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int
    //TODO  builtBY
)