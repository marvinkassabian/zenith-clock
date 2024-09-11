package dev.marvinkassabian.zenithclock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_item, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }

    class AlarmViewHolder(alarmView: View) : RecyclerView.ViewHolder(alarmView) {
        private val alarmHour: TextView = alarmView.findViewById(R.id.alarmHour)
        private val alarmMinute: TextView = alarmView.findViewById(R.id.alarmMinute)

        fun bind(alarm: Alarm) {
            alarmHour.text = String.format(Locale.getDefault(), "%02d", alarm.hourOfDay)
            alarmMinute.text = String.format(Locale.getDefault(), "%02d", alarm.minute)
        }
    }

    class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }
    }
}
