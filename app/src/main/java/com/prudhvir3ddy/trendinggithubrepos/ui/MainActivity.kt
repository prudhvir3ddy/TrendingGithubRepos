package com.prudhvir3ddy.trendinggithubrepos.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.prudhvir3ddy.trendinggithubrepos.R.layout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    //mainViewModel.getData()
    mainViewModel.startWorkManager()

    //on force refresh call viemodel.getDataByRemovingcache

    mainViewModel.data.observe(this, Observer {
      Log.i("Check", it.size.toString())
    })

  }
}
