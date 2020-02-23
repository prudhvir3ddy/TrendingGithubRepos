package com.prudhvir3ddy.trendinggithubrepos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants
import kotlinx.coroutines.launch

class MainViewModel(
  private val repo: MainRepo,
  private val workManager: WorkManager,
  private val workRequest: PeriodicWorkRequest
) : ViewModel() {

  val data: LiveData<List<UIResponse>> = repo.getTrendingReposFromDatabase()

  private fun getDataByRemovingCache() {
    viewModelScope.launch {
      repo.getTrendingRepos()
    }
  }

  fun startWorkManager() {
    workManager.enqueueUniquePeriodicWork(AppConstants.WORKER_TAG, KEEP, workRequest)
  }

}