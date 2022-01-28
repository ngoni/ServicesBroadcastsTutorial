package com.scribblex.tutorial.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.work.*
import com.scribblex.tutorial.workers.BackgroundWorker
import java.util.concurrent.TimeUnit

/**
 * This is a background service that uses WorkManager
 * to perform periodic work
 */

class BackgroundServiceExample : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startBackgroundWork()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startBackgroundWork() {
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val repeatInterval: Long = 15
        val uniqueWorkName = "StartMyBackgroundWork"
        val periodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            BackgroundWorker::class.java,
            repeatInterval, TimeUnit.MINUTES
        ).build()

        val workManager: WorkManager = WorkManager.getInstance(this)
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

}