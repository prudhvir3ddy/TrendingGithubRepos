package com.prudhvir3ddy.trendinggithubrepos.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UIResponse::class], version = 1, exportSchema = false)
abstract class TrendingRepoDatabase : RoomDatabase() {

  abstract fun repoDao(): RepoDao

}