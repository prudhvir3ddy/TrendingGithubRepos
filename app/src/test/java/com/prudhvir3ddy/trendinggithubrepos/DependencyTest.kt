package com.prudhvir3ddy.trendinggithubrepos

import android.app.Application
import com.prudhvir3ddy.trendinggithubrepos.di.databaseModule
import com.prudhvir3ddy.trendinggithubrepos.di.networkModule
import com.prudhvir3ddy.trendinggithubrepos.di.repoModule
import com.prudhvir3ddy.trendinggithubrepos.di.viewModelModule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

class DependencyTest : KoinTest {

  @Test
  fun testDependencies() {
    koinApplication {
      androidContext(mock(Application::class.java))
      modules(
        listOf(
          networkModule,
          viewModelModule,
          repoModule,
          databaseModule
        )
      )
    }.checkModules()
  }
}