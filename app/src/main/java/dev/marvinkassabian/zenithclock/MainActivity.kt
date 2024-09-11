package dev.marvinkassabian.zenithclock

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: AlarmAdapter
    private val alarmViewModel: AlarmViewModel by viewModels()
    private lateinit var alarmManagerHelper: AlarmManagerHelper
    private lateinit var timePickerHelper: TimePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmManagerHelper = AlarmManagerHelper(this)
        timePickerHelper = TimePickerHelper(this) { hourOfDay, minute ->
            alarmViewModel.addAlarm(hourOfDay, minute)
            alarmManagerHelper.scheduleAlarm(hourOfDay, minute)
        }

        recyclerView = findViewById(R.id.alarmRecyclerView)
        fab = findViewById(R.id.fabAddAlarm)

        adapter = AlarmAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        alarmViewModel.alarms.observe(this, Observer { alarms ->
            adapter.submitList(alarms)
        })

        fab.setOnClickListener {
            timePickerHelper.showTimePicker()
        }
    }
}
