package com.scribblex.tutorial.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*


class BoundBackgroundServiceExample : Service() {

    private val binder = LocalBinder()

    // Random number generator
    private val generator = Random()

    /** method for clients  */
    val randomNumber: Int
        get() = generator.nextInt(100)

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        fun getService(): BoundBackgroundServiceExample = this@BoundBackgroundServiceExample
    }

}