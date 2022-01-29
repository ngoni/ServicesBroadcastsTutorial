package com.scribblex.tutorial.ui

import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.scribblex.tutorial.R
import com.scribblex.tutorial.receivers.AirPlaneModeReceiver
import com.scribblex.tutorial.receivers.LocalBroadCastReceiver
import com.scribblex.tutorial.services.BackgroundServiceExample
import com.scribblex.tutorial.services.BoundBackgroundServiceExample
import com.scribblex.tutorial.services.ForegroundServiceExample
import com.scribblex.tutorial.utils.Constants

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var airPlaneModeReceiver: AirPlaneModeReceiver
    private lateinit var localBroadCastReceiver: LocalBroadCastReceiver
    private lateinit var foregroundServiceIntent: Intent
    private lateinit var backgroundServiceIntent: Intent

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as BoundBackgroundServiceExample.LocalBinder
            val num: Int = binder.getService().randomNumber
            Log.d(TAG, "Bound Service Connected - Number: $num")
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            Log.d(TAG, "Bound Service Disconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        triggerLocalBroadcast()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanUpComponents()
    }

    override fun onStart() {
        super.onStart()
        startBoundBackgroundService()
    }

    override fun onStop() {
        cancelBoundBackgroundService()
        super.onStop()
    }

    private fun initComponents() {
        initServices()
        initReceivers()
    }

    private fun initReceivers() {
        initAirplaneModeReceiver()
        initLocalBroadcastReceiver()
    }

    private fun initServices() {
        startForegroundService()
        startBackgroundService()
    }

    private fun initLocalBroadcastReceiver() {
        val localBroadcastFilter = IntentFilter().apply {
            addAction(Constants.ACTION_LOCAL_BROADCAST)
        }
        localBroadCastReceiver = LocalBroadCastReceiver()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(localBroadCastReceiver, localBroadcastFilter)
    }

    private fun initAirplaneModeReceiver() {
        val airplaneModeFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        airPlaneModeReceiver = AirPlaneModeReceiver()
        registerReceiver(airPlaneModeReceiver, airplaneModeFilter)
    }

    private fun triggerLocalBroadcast() {
        Intent().apply {
            action = Constants.ACTION_LOCAL_BROADCAST
        }.also {
            LocalBroadcastManager.getInstance(this).sendBroadcast(it)
        }
    }

    private fun startForegroundService() {
        // is it fine to run the below many times?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            foregroundServiceIntent = Intent(this, ForegroundServiceExample::class.java)
            startForegroundService(foregroundServiceIntent)
        }
    }

    private fun cancelForegroundService() {
        stopService(foregroundServiceIntent)
    }

    private fun startBackgroundService() {
        backgroundServiceIntent = Intent(this, BackgroundServiceExample::class.java).also {
            startService(it)
        }
    }

    private fun cancelBackgroundService() {
        stopService(backgroundServiceIntent)
    }

    private fun startBoundBackgroundService() {
        Intent(this, BoundBackgroundServiceExample::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun cancelBoundBackgroundService() {
        unbindService(connection)
    }

    private fun cleanUpComponents() {
        unregisterReceiver(airPlaneModeReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadCastReceiver)
        cancelForegroundService()
        cancelBackgroundService()
    }
}