package dev.marvinkassabian.zenithclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver", "Alarm triggered")
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show()
    }
}