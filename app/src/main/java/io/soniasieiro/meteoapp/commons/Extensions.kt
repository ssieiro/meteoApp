package io.soniasieiro.meteoapp.commons

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun showSnackbar(activity: Activity, stringId: Int, actionId: Int, listener: View.OnClickListener) {
    Snackbar.make(
            activity.findViewById(android.R.id.content),
            activity.getString(stringId),
            Snackbar.LENGTH_INDEFINITE).setAction(activity.getString(actionId), listener).show()
}

fun getDate (timestamp: Long?): String {
    var date = DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.ofEpochSecond(timestamp!!))
    return date
}