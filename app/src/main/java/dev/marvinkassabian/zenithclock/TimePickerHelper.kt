package dev.marvinkassabian.zenithclock

import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar

class TimePickerHelper(private val context: Context, private val onTimeSet: (Int, Int) -> Unit) {

    fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context, { _, hourOfDay, minute ->
                onTimeSet(hourOfDay, minute)
            }, currentHour, currentMinute, false
        )

        timePickerDialog.show()
    }
}
