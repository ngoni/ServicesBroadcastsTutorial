package com.scribblex.tutorial.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.scribblex.tutorial.MainActivity
import com.scribblex.tutorial.R

class ForegroundServiceExample : Service() {

    companion object {
        private const val ONGOING_NOTIFICATION = 1
        private const val FOREGROUND_SERVICE_CHANNEL = "FOREGROUND_SERVICE_CHANNEL"
        private const val FOREGROUND_CHANNEL = "Foreground Notifications"
    }

    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(ONGOING_NOTIFICATION, buildNotification())
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopSelf()
        super.onDestroy()
    }

    private fun buildNotification(): Notification {
        val pendingIntent: PendingIntent =
            Intent(applicationContext, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification: Notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, FOREGROUND_SERVICE_CHANNEL).apply {
                    setContentTitle(getString(R.string.foreground_service_title))
                    setContentIntent(pendingIntent)
                    setSmallIcon(R.drawable.ic_launcher_foreground)
                }.build()

            } else {
                // TODO: Implement solution for older API's
                Notification.Builder(this).build()
            }
        return notification
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            FOREGROUND_SERVICE_CHANNEL,
            FOREGROUND_CHANNEL,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor = Color.BLUE
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}