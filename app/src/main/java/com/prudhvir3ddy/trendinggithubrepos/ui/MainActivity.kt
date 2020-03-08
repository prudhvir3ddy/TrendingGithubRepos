package com.prudhvir3ddy.trendinggithubrepos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.prudhvir3ddy.trendinggithubrepos.R
import com.prudhvir3ddy.trendinggithubrepos.R.layout
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import kotlinx.android.synthetic.main.activity_main.recyclerview
import kotlinx.android.synthetic.main.activity_main.swipe_refresh
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by viewModel()
  lateinit var mainRecyclerViewAdapter: MainRecyclerViewAdapter
  lateinit var mObserver: Observer<PagedList<UIResponse>>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)

    swipe_refresh.setOnRefreshListener {
      mainViewModel.cancelWorkManagerAndStart()
    }

    mainRecyclerViewAdapter = MainRecyclerViewAdapter()
    recyclerview.adapter = mainRecyclerViewAdapter

    recyclerview.addItemDecoration(
      DividerItemDecoration(
        recyclerview.context,
        LinearLayoutManager.VERTICAL
      )
    )
    mainViewModel.startWorkManager()

    mObserver = Observer {
      stopRefresh()
      mainRecyclerViewAdapter.submitList(it)
    }
    mainViewModel.data.observe(this, mObserver)
  }

  private fun stopRefresh() {
    swipe_refresh.isRefreshing = false
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.sort_by_name -> {
        removeObserver()
        mainViewModel.sortListByName()
        addObserver()
        true
      }
      R.id.sort_by_stars -> {
        removeObserver()
        mainViewModel.sortListByStars()
        addObserver()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun addObserver() = mainViewModel.data.observe(this, mObserver)

  private fun removeObserver() = mainViewModel.data.removeObserver(mObserver)


}
