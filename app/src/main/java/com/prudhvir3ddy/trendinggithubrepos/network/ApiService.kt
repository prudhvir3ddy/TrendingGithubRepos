package com.prudhvir3ddy.trendinggithubrepos.network

import com.prudhvir3ddy.trendinggithubrepos.model.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

  @GET("/repositories")
  suspend fun getTrendingRepos(): Response<List<NetworkResponse>>
}