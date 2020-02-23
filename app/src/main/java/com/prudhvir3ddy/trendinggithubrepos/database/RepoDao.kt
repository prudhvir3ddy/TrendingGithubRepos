package com.prudhvir3ddy.trendinggithubrepos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RepoDao {

  @Insert
  suspend fun insertRepos(data: List<UIResponse>)

  @Query("SELECT * FROM trending_repos")
  fun getAll(): LiveData<List<UIResponse>>

  @Query("DELETE FROM trending_repos")
  suspend fun deleteAll()
}