package com.prudhvir3ddy.trendinggithubrepos.di

import android.content.Context
import com.prudhvir3ddy.trendinggithubrepos.database.RepoDao
import com.prudhvir3ddy.trendinggithubrepos.database.TrendingRepoDatabase
import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRepo
import com.prudhvir3ddy.trendinggithubrepos.ui.MainViewModel
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val viewModelModule = module {

  viewModel {
    MainViewModel(get())
  }

}

val repoModule = module {
  single {
    MainRepo(get())
  }
}

val networkModule = module {
  single {
    provideRetrofit(get())
  }

  single {
    provideApiService(get())
  }

  single {
    provideMoshiConverterFactory()
  }
}

val databaseModule = module {
  single {
    provideTrendingRepoDao(get())
  }
}

private fun provideTrendingRepoDao(context: Context): RepoDao {
  return TrendingRepoDatabase.getInstance(context).repoDao()
}

private fun provideApiService(retrofit: Retrofit): ApiService {
  return retrofit.create(ApiService::class.java)
}

private fun provideMoshiConverterFactory(): MoshiConverterFactory {
  return MoshiConverterFactory.create()
}

private fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory): Retrofit {
  return Retrofit
    .Builder()
    .addConverterFactory(moshiConverterFactory)
    .baseUrl(BASE_URL)
    .build()
}

