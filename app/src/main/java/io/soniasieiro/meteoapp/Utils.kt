package io.soniasieiro.meteoapp

import android.annotation.SuppressLint
import android.os.Build
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Utils {

    fun getDate (timestamp: Long?): String {
        var date = DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.ofEpochSecond(timestamp!!))
        return date
    }

}