package com.mkpazon.businessdayscalculator.data.model

import org.junit.Assert
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class HolidayTest {
    @Test
    fun testDateBasedHoliday() {
        val holiday = DateBasedHoliday(JANUARY, 1)

        val date = Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DATE, 1)
            set(Calendar.YEAR, 2019)
        }.time

        Assert.assertTrue(holiday.isHoliday(date))
    }

    @Test
    fun testMoveToMondayHoliday() {
        val holidayWednesday = MoveToMondayHoliday(DECEMBER, 4)
        val holidayFriday = MoveToMondayHoliday(DECEMBER, 6)
        val holidaySunday = MoveToMondayHoliday(DECEMBER, 7)
        val holidaySaturday = MoveToMondayHoliday(DECEMBER, 8)

        val date = Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.DATE, 9)
            set(Calendar.YEAR, 2019)
        }.time

        Assert.assertTrue(holidaySaturday.isHoliday(date))
        Assert.assertTrue(holidaySunday.isHoliday(date))
        Assert.assertFalse(holidayWednesday.isHoliday(date))
        Assert.assertFalse(holidayFriday.isHoliday(date))
    }

    @Test
    fun testCertainDayHoliday() {
        val holiday = CertainDayHoliday(Calendar.AUGUST, 3, THURSDAY)

        val date2018 = Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.AUGUST)
            set(Calendar.DATE, 16)
            set(Calendar.YEAR, 2018)
        }.time

        val date2019 = Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.AUGUST)
            set(Calendar.DATE, 15)
            set(Calendar.YEAR, 2019)
        }.time

        val date2020 = Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.AUGUST)
            set(Calendar.DATE, 20)
            set(Calendar.YEAR, 2020)
        }.time

        Assert.assertTrue(holiday.isHoliday(date2018))
        Assert.assertTrue(holiday.isHoliday(date2019))
        Assert.assertTrue(holiday.isHoliday(date2020))

    }
}