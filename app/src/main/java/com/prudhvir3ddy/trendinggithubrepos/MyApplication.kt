package com.prudhvir3ddy.trendinggithubrepos

import android.app.Application
import com.prudhvir3ddy.trendinggithubrepos.di.networkModule
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
              networkModule
          )
      )
    }
  }
}