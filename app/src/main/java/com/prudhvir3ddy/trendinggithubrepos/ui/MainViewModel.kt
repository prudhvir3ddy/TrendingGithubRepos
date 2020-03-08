package com.prudhvir3ddy.trendinggithubrepos.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.prudhvir3ddy.trendinggithubrepos.FetchDataWorker
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants
import java.util.concurrent.TimeUnit.HOURS

class MainViewModel(
  private val repo: MainRepo,
  private val context: Context
) : ViewModel() {

  var data: LiveData<PagedList<UIResponse>> = repo.getTrendingReposFromDatabase()

  fun startWorkManager() {
    val workManager = WorkManager.getInstance(context)
    val workRequest =
      PeriodicWorkRequest.Builder(FetchDataWorker::class.java, AppConstants.WORK_TIME, HOURS)
        .setConstraints(
          Constraints.Builder()
            .setRequiredNetworkType(CONNECTED)
            .build()
        ).addTag(AppConstants.WORKER_TAG)
        .build()
    workManager.enqueueUniquePeriodicWork(AppConstants.WORKER_TAG, KEEP, workRequest)
  }

  fun cancelWorkManagerAndStart() {
    val workManager = WorkManager.getInstance(context)
    workManager.cancelAllWorkByTag(AppConstants.WORKER_TAG)
    startWorkManager()
  }

  private fun refreshData() {
    data = repo.getTrendingReposFromDatabase()
  }

  fun sortListByStars() {
    repo.sortListByStars()
    refreshData()
  }

  fun sortListByName() {
    repo.sortListByName()
    refreshData()
  }

}