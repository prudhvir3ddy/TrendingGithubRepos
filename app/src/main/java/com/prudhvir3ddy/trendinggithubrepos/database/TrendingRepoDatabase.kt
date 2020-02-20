package com.prudhvir3ddy.trendinggithubrepos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UIResponse::class], version = 1)
abstract class TrendingRepoDatabase : RoomDatabase() {

  abstract fun repoDao(): RepoDao

  companion object {

    private const val DATABASE_NAME = "trending_repos.db"

    @Volatile
    private var instance: TrendingRepoDatabase? = null

    fun getInstance(context: Context): TrendingRepoDatabase {
      return instance ?: buildDatabase(context).also { instance = it }
    }

    private fun buildDatabase(context: Context): TrendingRepoDatabase {
      return Room.databaseBuilder(context, TrendingRepoDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
    }
  }
}