package com.prudhvir3ddy.trendinggithubrepos.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface RepoDao {

  @Insert
  suspend fun insertRepos(data: List<UIResponse>)

  @RawQuery(observedEntities = [UIResponse::class])
  fun getAll(query: SupportSQLiteQuery?): DataSource.Factory<Int, UIResponse>

  @Query("DELETE FROM trending_repos")
  suspend fun deleteAll()
}