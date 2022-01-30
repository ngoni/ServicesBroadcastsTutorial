package com.scribblex.tutorial.data.remote

import com.scribblex.tutorial.data.models.Categories
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun fetchCatalogData(): Response<List<Categories>>
}