package com.prudhvir3ddy.trendinggithubrepos

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FetchDataWorker(context: Context, workerParams: WorkerParameters) : Worker(
  context,
  workerParams
), KoinComponent {

  private val job = Job()
  private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

  override fun doWork(): Result {

    val mainRepo: MainRepo by inject()

    coroutineScope.launch {
      mainRepo.getTrendingRepos()
    }

    return Result.success()
  }
}
