package com.prudhvir3ddy.trendinggithubrepos

import android.app.Application
import com.prudhvir3ddy.trendinggithubrepos.di.databaseModule
import com.prudhvir3ddy.trendinggithubrepos.di.networkModule
import com.prudhvir3ddy.trendinggithubrepos.di.repoModule
import com.prudhvir3ddy.trendinggithubrepos.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    koinInit()

  }

  private fun koinInit() {
    startKoin {
      // declare used Android context
      androidContext(this@MyApplication)
      // declare modules
      modules(
        listOf(
          networkModule,
          viewModelModule,
          repoModule,
          databaseModule
        )
      )
    }
  }
}