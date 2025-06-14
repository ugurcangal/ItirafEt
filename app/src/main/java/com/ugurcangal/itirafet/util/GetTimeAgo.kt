package com.ugurcangal.itirafet.util

import com.google.firebase.Timestamp
import java.util.concurrent.TimeUnit


fun getTimeAgo(timestamp: Timestamp): String {
    val now = System.currentTimeMillis()
    val time = timestamp.toDate().time
    val diff = now - time

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "şimdi"
        diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} dakika önce"
        diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)} saat önce"
        diff < TimeUnit.DAYS.toMillis(7) -> "${TimeUnit.MILLISECONDS.toDays(diff)} gün önce"
        else -> {
            val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            dateFormat.format(timestamp.toDate())
        }
    }
}