package com.github.ojh.overtime.alarm

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver


class AlarmReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val hourOfDay = intent?.getIntExtra(AlarmUtil.KEY_HOUR_OF_DAY, -1)

        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(AlarmUtil.KEY_HOUR_OF_DAY, hourOfDay)
        }
        context?.startService(serviceIntent)
    }
}