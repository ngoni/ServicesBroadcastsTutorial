package com.scribblex.tutorial.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStarted
import com.scribblex.tutorial.data.repository.DataStoreRepository
import kotlinx.coroutines.launch

private const val TAG = "DataStoreActivity"

class DataStoreActivity : AppCompatActivity() {

    private val dataStoreRepository: DataStoreRepository by lazy {
        DataStoreRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataStoreRepository.counter.collect { counter ->
                    Log.d(TAG, "Counter: $counter")
                }
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        incrementCounter()
    }

    private fun incrementCounter() {
        lifecycleScope.launch {
            whenStarted {
                dataStoreRepository.incrementCounter()
            }
        }
    }
}