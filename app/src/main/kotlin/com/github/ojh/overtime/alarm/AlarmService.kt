package com.github.ojh.overtime.alarm

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import com.github.ojh.overtime.R
import com.github.ojh.overtime.splash.SplashActivity

class AlarmService : IntentService("AlarmService") {

    override fun onHandleIntent(intent: Intent?) {
        val hourOfDay = intent?.getIntExtra(AlarmUtil.KEY_HOUR_OF_DAY, -1)

        if (hourOfDay != null && hourOfDay != -1) {
            AlarmUtil.setOnceAlarm(this, hourOfDay)
            showNotification()
        }
    }

    private fun showNotification() {

        val splashIntent = Intent(this, SplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, splashIntent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setContentTitle("일기를 쓸 시간이다.")
                .setContentText("쓰쟝쓰쟝")
                .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(AlarmUtil.ID_NOTIFICATION, notification)

    }

}