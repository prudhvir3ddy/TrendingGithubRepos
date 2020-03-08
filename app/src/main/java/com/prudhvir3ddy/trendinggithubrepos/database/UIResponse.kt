package com.prudhvir3ddy.trendinggithubrepos.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants

@Entity(tableName = AppConstants.TABLE_NAME)
data class UIResponse(
  val author: String,
  val name: String,
  val avatar: String,
  @PrimaryKey
  val url: String,
  val description: String,
  val language: String?,
  val languageColor: String?,
  val stars: Int,
  val forks: Int,
  val currentPeriodStars: Int
)

