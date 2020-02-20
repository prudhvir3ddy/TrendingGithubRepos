package com.prudhvir3ddy.trendinggithubrepos.di

import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

