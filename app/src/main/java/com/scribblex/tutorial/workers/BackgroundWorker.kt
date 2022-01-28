package com.scribblex.tutorial.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val TAG = "BackgroundWorker"

class BackgroundWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "Doing some background work")
        return Result.success()
    }
}