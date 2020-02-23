package com.prudhvir3ddy.trendinggithubrepos.ui

import android.util.Log
import androidx.lifecycle.LiveData
import com.prudhvir3ddy.trendinggithubrepos.database.RepoDao
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import com.prudhvir3ddy.trendinggithubrepos.utils.toUIResponse

class MainRepo(
  private val apiService: ApiService,
  private val repoDao: RepoDao
) {
  suspend fun getTrendingRepos() {
    Log.d("boom", "Iam here")
    val response = apiService.getTrendingRepos()
    if (response.isSuccessful) {
      repoDao.deleteAll()
      repoDao.insertRepos(response.body()!!.map {
        it.toUIResponse()
      })
    } else {
      //TODO handle failure
      Log.d("Network Failure", "something went wrong!")
    }

  }

  fun getTrendingReposFromDatabase(): LiveData<List<UIResponse>> {
    return repoDao.getAll()
  }

}