package com.prudhvir3ddy.trendinggithubrepos.database

import androidx.room.Entity

@Entity(tableName = "trending_repos")
data class UIResponse(
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
)

