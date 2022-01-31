package com.scribblex.tutorial.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DataStoreRepositoryTest {

    private lateinit var dataStoreRepository: DataStoreRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataStoreRepository = DataStoreRepository(context)
    }

    @Test
    fun testIncrementCount(): Unit = runBlocking {
        dataStoreRepository.incrementCounter()
        Assert.assertEquals(1, dataStoreRepository.counter.first())
    }
}