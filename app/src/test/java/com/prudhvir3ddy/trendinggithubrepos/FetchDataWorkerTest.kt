package com.prudhvir3ddy.trendinggithubrepos

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import com.prudhvir3ddy.trendinggithubrepos.di.databaseModule
import com.prudhvir3ddy.trendinggithubrepos.di.networkModule
import com.prudhvir3ddy.trendinggithubrepos.di.repoModule
import com.prudhvir3ddy.trendinggithubrepos.di.workModule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class FetchDataWorkerTest : KoinTest {

  private lateinit var context: Context
  private lateinit var mockWebServer: MockWebServer

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    startKoin {
      // declare used Android context
      androidContext(context)
      // declare modules
      modules(
        listOf(
          networkModule,
          repoModule,
          databaseModule,
          workModule
        )
      )
    }
    mockWebServer = get()

    mockWebServer.start()
    mockWebServer.enqueue(MockResponse().setBody(Responses.response))
  }

  @Test
  fun testFetchDataWorker() {
    val worker = TestListenableWorkerBuilder<FetchDataWorker>(
      context
    ).build()
    runBlocking {
      val result = worker.doWork()
      assert(result == (Result.success()))
    }
  }

  @After
  fun teardown() {
    mockWebServer.shutdown()
  }
}