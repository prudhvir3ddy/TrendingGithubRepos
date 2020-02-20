package com.prudhvir3ddy.trendinggithubrepos.ui

import com.prudhvir3ddy.trendinggithubrepos.network.ApiService

class MainRepo(val apiService: ApiService) {

  suspend fun getTrendingRepos() {

    val response = apiService.getTrendingRepos()
    if (response.isSuccessful) {
      //TODO handle success

    } else {
      //TODO handle failure
    }
  }

}