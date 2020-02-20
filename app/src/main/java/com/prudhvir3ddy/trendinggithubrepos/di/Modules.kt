package com.prudhvir3ddy.trendinggithubrepos.di

import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
  single {
    provideRetrofit()
  }
}

private fun provideRetrofit(): Retrofit {

  return Retrofit
      .Builder()
      .baseUrl(BASE_URL)
      .build()
}

