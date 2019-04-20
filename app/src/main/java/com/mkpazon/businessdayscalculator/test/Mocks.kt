package com.mkpazon.businessdayscalculator.test

import com.mkpazon.businessdayscalculator.data.model.CertainDayHoliday
import com.mkpazon.businessdayscalculator.data.model.DateBasedHoliday
import com.mkpazon.businessdayscalculator.data.model.Holiday
import com.mkpazon.businessdayscalculator.data.model.MoveToMondayHoliday
import java.util.Calendar.*

object Mocks {

    fun getNSWHolidays(): List<Holiday> {
        return listOf(
            DateBasedHoliday(JANUARY, 1),
            MoveToMondayHoliday(JANUARY, 26),
            DateBasedHoliday(APRIL, 19),
            DateBasedHoliday(APRIL, 20),
            DateBasedHoliday(APRIL, 21),
            DateBasedHoliday(APRIL, 22),
            DateBasedHoliday(APRIL, 25),
            CertainDayHoliday(JUNE, 2, MONDAY),
            CertainDayHoliday(AUGUST, 1, MONDAY),
            CertainDayHoliday(OCTOBER, 1, MONDAY),
            DateBasedHoliday(DECEMBER, 25),
            DateBasedHoliday(DECEMBER, 26)
        )
    }
}