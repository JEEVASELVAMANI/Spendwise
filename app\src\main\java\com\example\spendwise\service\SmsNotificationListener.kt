package com.example.spendwise.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.spendwise.data.SmsParser
import com.example.spendwise.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsNotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notif = sbn.notification
        val extras = notif.extras
        val title = extras.getString("android.title") ?: ""
        val text = extras.getCharSequence("android.text")?.toString() ?: ""
        val content = "$title: $text"

        val txn = SmsParser.parse(content)
        // Insert into DB on background thread
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(applicationContext).transactionDao().insert(txn)
        }
    }
}
