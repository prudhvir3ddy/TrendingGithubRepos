package com.prudhvir3ddy.trendinggithubrepos.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.prudhvir3ddy.trendinggithubrepos.SharedPrefs
import com.prudhvir3ddy.trendinggithubrepos.database.TrendingRepoDatabase
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants
import com.prudhvir3ddy.trendinggithubrepos.utils.toUIResponse

class MainRepo(
  private val apiService: ApiService,
  private val database: TrendingRepoDatabase,
  private val sharedPrefs: SharedPrefs
) {
  suspend fun getTrendingRepos() {
    val response = apiService.getTrendingRepos()
    if (response.isSuccessful) {
      database.repoDao().deleteAll()
      database.repoDao().insertRepos(response.body()!!.map {
        it.toUIResponse()
      })
    } else {
      //TODO handle failure
      Log.d("Network Failure", "something went wrong!")
    }

  }

  fun getTrendingReposFromDatabase(): LiveData<PagedList<UIResponse>> {
    val sortBy = sharedPrefs.getSort()
    val factory: DataSource.Factory<Int, UIResponse> =
      database.repoDao().getAll(getAllQuery(sortBy))
    return LivePagedListBuilder<Int, UIResponse>(
      factory, AppConstants.PAGE_SIZE
    ).build()
  }

  private fun getAllQuery(sortBy: String?): SimpleSQLiteQuery? {
    val queryBuilder = SupportSQLiteQueryBuilder
      .builder(AppConstants.TABLE_NAME)
      .orderBy(sortBy)
    return SimpleSQLiteQuery(queryBuilder.create().sql)
  }

  fun sortListByStars() {
    sharedPrefs.setSort("stars")
  }

  fun sortListByName() {
    sharedPrefs.setSort("name")
  }

}