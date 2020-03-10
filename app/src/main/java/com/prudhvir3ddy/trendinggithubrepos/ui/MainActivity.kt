package com.prudhvir3ddy.trendinggithubrepos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.prudhvir3ddy.trendinggithubrepos.R
import com.prudhvir3ddy.trendinggithubrepos.R.layout
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.utils.NetworkChecker
import kotlinx.android.synthetic.main.activity_main.error_layout
import kotlinx.android.synthetic.main.activity_main.recyclerview
import kotlinx.android.synthetic.main.activity_main.shimmer
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
      shimmer.startShimmer()
      shimmer.visibility = View.VISIBLE
      swipe_refresh.visibility = View.GONE
      mainViewModel.cancelWorkManagerAndStart()
    }

    setupRecyclerView()

    mainViewModel.startWorkManager()

    mObserver = Observer {
      stopRefresh()
      if (it.snapshot().isEmpty()) {
        error_layout.visibility = View.VISIBLE
        swipe_refresh.visibility = View.GONE
      } else {
        error_layout.visibility = View.GONE
        swipe_refresh.visibility = View.VISIBLE
        mainRecyclerViewAdapter.submitList(it)
      }
    }
    addObserver()
  }

  private fun setupRecyclerView() {
    mainRecyclerViewAdapter = MainRecyclerViewAdapter()
    recyclerview.adapter = mainRecyclerViewAdapter

    recyclerview.addItemDecoration(
      DividerItemDecoration(
        recyclerview.context,
        LinearLayoutManager.VERTICAL
      )
    )
  }

  private fun stopRefresh() {
    shimmer.stopShimmer()
    shimmer.visibility = View.GONE
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

  override fun onDestroy() {
    super.onDestroy()
    NetworkChecker.unregisterCallback()
  }
}
