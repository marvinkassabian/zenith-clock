package dev.marvinkassabian.zenithclock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log


class AlarmViewModel : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>(emptyList())
    val alarms: LiveData<List<Alarm>> get() = _alarms

    fun addAlarm(hourOfDay: Int, minute: Int) {
        val currentList = _alarms.value ?: emptyList()
        Log.d("AlarmViewModel",currentList.toString())
        val updatedList = currentList + Alarm(hourOfDay, minute)
        _alarms.value = updatedList
    }
}
