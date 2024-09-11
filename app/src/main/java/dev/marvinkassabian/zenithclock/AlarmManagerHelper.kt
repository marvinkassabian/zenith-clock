package dev.marvinkassabian.zenithclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.util.Calendar
import java.util.Locale

class AlarmManagerHelper(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(hourOfDay: Int, minute: Int) {
        try {
            if (checkExactAlarmPermission()) {
                val alarmIntent = Intent(context, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE
                )

                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    if (timeInMillis < System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }

                val formattedTime =
                    String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                Toast.makeText(context, "Alarm set for $formattedTime", Toast.LENGTH_SHORT).show()
                Log.d("AlarmManagerHelper", "Scheduling alarm for ${calendar.time}")

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            } else {
                requestExactAlarmPermission()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(context, "Permission not granted for exact alarms", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkExactAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        }
    }
}
