package com.prudhvir3ddy.trendinggithubrepos.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.prudhvir3ddy.trendinggithubrepos.R.layout
import com.prudhvir3ddy.trendinggithubrepos.network.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import javax.net.ssl.SSLHandshakeException

class MainActivity : AppCompatActivity() {

  val service: ApiService by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    GlobalScope.launch {
      try {
        val response = service.getTrendingRepos()
      } catch (e: SSLHandshakeException) {
        Log.d("boom", e.message)
      }
    }

  }
}
