package com.sam.watermyplant.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.convertToString(): String {
    return DateTimeFormatter
        .ofPattern("hh:mm")
        .format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.convertToString(): String {
    return DateTimeFormatter
        .ofPattern("MMM dd yyyy")
        .format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertToLocalDate(): LocalDate {
    return try {
        val formatter = DateTimeFormatter
            .ofPattern("MMM dd yyyy")
        LocalDate.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        Log.d("DateTimeParserException",e.localizedMessage!!)
        LocalDate.now()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertToLocalTime(): LocalTime {
    return try {
        val formatter = DateTimeFormatter
            .ofPattern("hh:mm")
        LocalTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        Log.d("DateTimeParserException", e.localizedMessage!!)
        LocalTime.NOON
    }
}







