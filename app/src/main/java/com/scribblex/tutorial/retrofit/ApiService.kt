package com.scribblex.tutorial.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun fetchCatalogData(): Response<List<Categories>>
}