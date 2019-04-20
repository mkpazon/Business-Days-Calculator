package com.mkpazon.businessdayscalculator.util

import java.util.*

fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        time = this@toCalendar
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}

fun Date.set(year: Int, month: Int, date: Int): Date {
    val calendar = toCalendar().apply { set(year, month, date) }
    this.time = calendar.timeInMillis
    return this
}