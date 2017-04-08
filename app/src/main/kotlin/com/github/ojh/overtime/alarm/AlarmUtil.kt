package com.github.ojh.overtime.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*


object AlarmUtil {

    const val ID_NOTIFICATION = 1
    const val REQ_ALARM = 100
    const val KEY_HOUR_OF_DAY = "key_hour_of_day"

    const val DEFAULT_ALARM_HOUR = 22
    const val DEFAULT_ALARM_MINUTE = 0

    fun setOnceAlarm(context: Context, hourOfDay: Int, minute: Int = 0) {
        cancelAlarm(context)
        val intent = Intent("com.github.ojh.overtime.ALARM").apply {
            putExtra(KEY_HOUR_OF_DAY, hourOfDay)
        }

        val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQ_ALARM,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )


        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), pendingIntent)
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), pendingIntent)
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent("com.github.ojh.overtime.ALARM")
        val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQ_ALARM,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun getTriggerAtMillis(hourOfDay: Int, minute: Int): Long {
        val currentCalendar = GregorianCalendar.getInstance() as GregorianCalendar
        val currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY)
        val currentMinute = currentCalendar.get(GregorianCalendar.MINUTE)

        if (currentHourOfDay < hourOfDay || currentHourOfDay == hourOfDay && currentMinute < minute)
            return getTimeInMillis(false, hourOfDay, minute)
        else
            return getTimeInMillis(true, hourOfDay, minute)
    }


    private fun getTimeInMillis(tomorrow: Boolean, hourOfDay: Int, minute: Int): Long {
        val calendar = GregorianCalendar.getInstance() as GregorianCalendar

        if (tomorrow)
            calendar.add(GregorianCalendar.DAY_OF_YEAR, 1)

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(GregorianCalendar.MINUTE, minute)
        calendar.set(GregorianCalendar.SECOND, 0)
        calendar.set(GregorianCalendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }
}
