package com.example.rass_education.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Pengingat Bina Desa"
        val message = intent.getStringExtra("message") ?: "Ada kegiatan pertanahan hari ini!"

        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(title, message)
    }
}
