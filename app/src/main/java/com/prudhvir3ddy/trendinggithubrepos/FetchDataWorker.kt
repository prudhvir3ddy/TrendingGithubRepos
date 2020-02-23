package com.prudhvir3ddy.trendinggithubrepos

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRepo
import org.koin.core.KoinComponent
import org.koin.core.inject

class FetchDataWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(
  context,
  workerParams
), KoinComponent {

  override suspend fun doWork(): Result {

    val mainRepo: MainRepo by inject()

    mainRepo.getTrendingRepos()

    return Result.success()
  }
}
