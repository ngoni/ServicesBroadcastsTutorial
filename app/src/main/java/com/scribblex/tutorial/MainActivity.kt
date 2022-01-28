package com.scribblex.tutorial

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.scribblex.tutorial.receivers.AirPlaneModeReceiver
import com.scribblex.tutorial.receivers.LocalBroadCastReceiver
import com.scribblex.tutorial.services.ForegroundServiceExample
import com.scribblex.tutorial.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var airPlaneModeReceiver: AirPlaneModeReceiver
    private lateinit var localBroadCastReceiver: LocalBroadCastReceiver
    private lateinit var foregroundServiceIntent: Intent

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

    private fun cleanUpComponents() {
        unregisterReceiver(airPlaneModeReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadCastReceiver)
        cancelForegroundService()
    }
}