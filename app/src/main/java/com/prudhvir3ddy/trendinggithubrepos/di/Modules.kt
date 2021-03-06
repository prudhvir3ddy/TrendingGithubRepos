package com.prudhvir3ddy.trendinggithubrepos.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.prudhvir3ddy.trendinggithubrepos.SharedPrefs
import com.prudhvir3ddy.trendinggithubrepos.database.TrendingRepoDatabase
import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRepo
import com.prudhvir3ddy.trendinggithubrepos.ui.MainViewModel
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val viewModelModule = module {
  viewModel { MainViewModel(get(), get()) }
}

val repoModule = module {
  single { MainRepo(get(), get(), get()) }
}

val networkModule = module {
  single { provideRetrofit(get()) }

  single { provideApiService(get()) }

  single { provideMoshiConverterFactory() }
}

val databaseModule = module {
  single { provideRoomDatabase(get()) }
}

val sharedPrefsModule = module {
  single { provideSharedPreferences(get()) }
  single { provideSharedPrefs(get()) }
}

private fun provideSharedPrefs(sharedPreferences: SharedPreferences): SharedPrefs {
  return SharedPrefs(sharedPreferences)
}

private fun provideSharedPreferences(context: Context): SharedPreferences {
  return context.getSharedPreferences(AppConstants.MYPREFS, Context.MODE_PRIVATE)
}
private fun provideRoomDatabase(context: Context): TrendingRepoDatabase {
  return Room.databaseBuilder(
    context, TrendingRepoDatabase::class.java,
    AppConstants.DATABASE_NAME
  )
    .fallbackToDestructiveMigration()
    .build()
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

