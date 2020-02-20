package com.prudhvir3ddy.trendinggithubrepos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prudhvir3ddy.trendinggithubrepos.R.layout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  val mainViewModel: MainViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

  }
}
