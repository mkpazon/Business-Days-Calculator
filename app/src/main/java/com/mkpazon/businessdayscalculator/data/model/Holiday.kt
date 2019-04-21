package com.mkpazon.businessdayscalculator.data.model

import com.mkpazon.businessdayscalculator.util.toCalendar
import java.util.*
import java.util.Calendar.*


interface Holiday {
    fun isHoliday(date: Date): Boolean
}

data class DateBasedHoliday(val month: Int, val date: Int) : Holiday {
    override fun isHoliday(date: Date): Boolean {

        val holidayCal = Date().toCalendar().apply{
            time = date
            set(Calendar.MONTH, month)
            set(Calendar.DATE, this@DateBasedHoliday.date)
        }
        val cal = date.toCalendar()

        return holidayCal.get(MONTH) == cal.get(MONTH) &&
                holidayCal.get(DATE) == cal.get(DATE)
    }
}

data class MoveToMondayHoliday(val month: Int, val date: Int) : Holiday {
    override fun isHoliday(date: Date): Boolean {

        val holidayCal = Date().toCalendar().apply{
            time = date
            set(Calendar.MONTH, month)
            set(Calendar.DATE, this@MoveToMondayHoliday.date)
        }
        val cal = date.toCalendar()

        while (holidayCal.get(Calendar.DAY_OF_WEEK) == SATURDAY || holidayCal.get(Calendar.DAY_OF_WEEK) == SUNDAY) {
            holidayCal.add(DATE, 1)
        }

        return holidayCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                holidayCal.get(Calendar.DATE) == cal.get(Calendar.DATE)
    }
}

data class CertainDayHoliday(val month: Int, val weekOfMonth: Int, val dayOfWeek: Int) :
    Holiday {
    override fun isHoliday(date: Date): Boolean {

        val cal = date.toCalendar()

        val holidayCal = Calendar.getInstance().apply {
            set(HOUR_OF_DAY, 0)
            set(YEAR, cal.get(YEAR))
            set(MONTH, month)
            set(DAY_OF_WEEK, dayOfWeek)
            set(DAY_OF_WEEK_IN_MONTH, weekOfMonth)
        }

        return holidayCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                holidayCal.get(Calendar.DATE) == cal.get(Calendar.DATE) &&
                holidayCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
    }
}