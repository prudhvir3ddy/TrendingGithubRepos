package com.prudhvir3ddy.trendinggithubrepos.di

import android.content.Context
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.prudhvir3ddy.trendinggithubrepos.FetchDataWorker
import com.prudhvir3ddy.trendinggithubrepos.database.TrendingRepoDatabase
import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRepo
import com.prudhvir3ddy.trendinggithubrepos.ui.MainViewModel
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants
import okhttp3.mockwebserver.MockWebServer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.HOURS

val viewModelModule = module {
  viewModel { MainViewModel(get(), get()) }
}

val repoModule = module {
  single { MainRepo(get(), get(), get()) }
}

val networkModule = module {
  single { provideRetrofit(get(), get()) }

  single { provideFakeApiService(get()) }

  single { provideMoshiConverterFactory() }

  single { provideMockWebServer() }
}

val databaseModule = module {
  single { provideRoomDatabase(get()) }
}

val workModule = module {
  single { provideWorkManager(get()) }

  single { providePeriodicWorkRequest() }
}

private fun providePeriodicWorkRequest(): PeriodicWorkRequest {
  return PeriodicWorkRequest.Builder(FetchDataWorker::class.java, 2, HOURS)
    .setConstraints(
      Constraints.Builder()
        .setRequiredNetworkType(CONNECTED)
        .build()
    ).addTag(AppConstants.WORKER_TAG)
    .build()
}

private fun provideWorkManager(context: Context): WorkManager {
  return WorkManager.getInstance(context)
}

private fun provideRoomDatabase(context: Context): TrendingRepoDatabase {
  return Room.inMemoryDatabaseBuilder(
    context, TrendingRepoDatabase::class.java
  )
    .fallbackToDestructiveMigration()
    .build()
}

private fun provideFakeApiService(retrofit: Retrofit): ApiService {
  return retrofit.create(ApiService::class.java)
}

private fun provideMoshiConverterFactory(): MoshiConverterFactory {
  return MoshiConverterFactory.create()
}

private fun provideMockWebServer(): MockWebServer {
  return MockWebServer()
}

private fun provideRetrofit(
  mockWebServer: MockWebServer,
  moshiConverterFactory: MoshiConverterFactory
): Retrofit {
  return Retrofit
    .Builder()
    .addConverterFactory(moshiConverterFactory)
    .baseUrl(mockWebServer.url("/"))
    .build()
}

