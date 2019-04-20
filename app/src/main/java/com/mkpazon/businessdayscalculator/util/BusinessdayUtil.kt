package com.mkpazon.businessdayscalculator.util

import com.mkpazon.businessdayscalculator.data.model.Holiday
import java.util.*
import java.util.Calendar.*

class BusinessdayUtil {

    companion object {
        fun getNumberOfBusinessDays(
            startDate: Date,
            endDate: Date,
            holidays: List<Holiday>,
            inclusive: Boolean = true
        ): Int {
            val calStart = startDate.toCalendar().apply {
                if (!inclusive) add(Calendar.DATE, 1)
            }
            val calEnd = endDate.toCalendar().apply {
                if (!inclusive) add(Calendar.DATE, -1)
            }

            var numOfBusinessdays = 0
            while (calStart.before(calEnd) || calStart == calEnd) {
                if (!isWeekend(calStart) && !isHoliday(calStart, holidays)) {
                    numOfBusinessdays += 1
                }

                calStart.add(Calendar.DATE, 1)
            }
            return numOfBusinessdays
        }

        private fun isHoliday(traverseCalendar: Calendar, holidays: List<Holiday>): Boolean {
            holidays.forEach { holiday ->
                if (holiday.isHoliday(traverseCalendar.time)) {
                    return true
                }
            }
            return false
        }

        private fun isWeekend(calendar: Calendar): Boolean {
            val dayOfWeek = calendar.get(DAY_OF_WEEK)
            return dayOfWeek == SATURDAY || dayOfWeek == SUNDAY
        }
    }
}