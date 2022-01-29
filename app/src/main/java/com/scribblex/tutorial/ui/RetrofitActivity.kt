package com.scribblex.tutorial.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.scribblex.tutorial.R
import com.scribblex.tutorial.retrofit.ApiService
import com.scribblex.tutorial.utils.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RetrofitActivity"

class RetrofitActivity : AppCompatActivity() {

    private val retrofit: Retrofit = setupRetrofit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retrofit_activity)
        run()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun run() {
        // TODO: Move logic into ViewModel
        val apiService = retrofit.create(ApiService::class.java)
        GlobalScope.launch {
            val results = apiService.fetchCatalogData()
            if (results.isSuccessful) {
                Log.d(TAG, "Results: ${results.body().toString()}")
            } else {
                Log.d(TAG, "Response: ${results.errorBody()}")
            }
        }
    }

    private fun setupRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

