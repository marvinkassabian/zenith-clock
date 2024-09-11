package dev.marvinkassabian.zenithclock

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

data class Alarm(val hourOfDay: Int, val minute: Int)

class AlarmViewHolder(alarmView: View) : RecyclerView.ViewHolder(alarmView) {
    val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
}

class AlarmAdapter(private val alarms: MutableList<Alarm>) : RecyclerView.Adapter<AlarmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_layout, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.titleTextView.text = alarm.hourOfDay.toString()
        holder.descriptionTextView.text = alarm.minute.toString()
    }

    override fun getItemCount(): Int {
        return alarms.size
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: RecyclerView.Adapter<AlarmViewHolder>
    private val alarms: MutableList<Alarm> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        recyclerView = findViewById(R.id.alarmRecyclerView)
        fab = findViewById(R.id.fabAddAlarm)

        adapter = AlarmAdapter(alarms)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this, { _, hourOfDay, minute ->
                alarms.add(Alarm(hourOfDay, minute))
                adapter.notifyItemInserted(alarms.size)
                Toast.makeText(this, "Alarm set for $hourOfDay $minute", Toast.LENGTH_SHORT).show()
            }, currentHour, currentMinute, false
        )

        timePickerDialog.show()
    }
}
