package com.mkpazon.businessdayscalculator.ui.util

import com.mkpazon.businessdayscalculator.data.model.CertainDayHoliday
import com.mkpazon.businessdayscalculator.data.model.DateBasedHoliday
import com.mkpazon.businessdayscalculator.data.model.Holiday
import com.mkpazon.businessdayscalculator.data.model.MoveToMondayHoliday
import com.mkpazon.businessdayscalculator.test.Mocks
import com.mkpazon.businessdayscalculator.util.BusinessdayUtil
import com.mkpazon.businessdayscalculator.util.set
import org.junit.Assert
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class BusinessdayUtilTest {

    private val fixedDateHolidays = listOf<Holiday>(
        DateBasedHoliday(APRIL, 9),
        DateBasedHoliday(APRIL, 17),
        DateBasedHoliday(MAY, 4),
        DateBasedHoliday(JULY, 3),
        DateBasedHoliday(AUGUST, 1)
    )

    private val moveToMondayHolidays = listOf<Holiday>(
        MoveToMondayHoliday(APRIL, 9),
        MoveToMondayHoliday(APRIL, 14),
        MoveToMondayHoliday(APRIL, 17),
        MoveToMondayHoliday(MAY, 4),
        MoveToMondayHoliday(JUNE, 8),
        MoveToMondayHoliday(JUNE, 23),
        MoveToMondayHoliday(JULY, 3),
        MoveToMondayHoliday(AUGUST, 1)
    )

    private val certainDayHolidays = listOf<Holiday>(
        CertainDayHoliday(APRIL, 2, FRIDAY),
        CertainDayHoliday(APRIL, 2, TUESDAY),
        CertainDayHoliday(APRIL, 3, TUESDAY)
    )

    private val mixedHolidays = listOf<Holiday>(
        MoveToMondayHoliday(APRIL, 9),
        CertainDayHoliday(APRIL, 3, TUESDAY),
        MoveToMondayHoliday(APRIL, 17),
        MoveToMondayHoliday(MAY, 4),
        DateBasedHoliday(JUNE, 8),
        DateBasedHoliday(JUNE, 23),
        MoveToMondayHoliday(JULY, 3),
        MoveToMondayHoliday(AUGUST, 1)
    )

    @Test
    fun getNumberOfBusinessDays_noHolidays() {
        val start = GregorianCalendar(2019, DECEMBER, 30).time
        val end = GregorianCalendar(2020, JANUARY, 10).time
        val result = BusinessdayUtil.getNumberOfBusinessDays(start, end, listOf(), inclusive = false)
        Assert.assertEquals(8, result)
    }

    @Test
    fun getNumberOfBusinessDays_fixedDateHolidays() {
        val start = GregorianCalendar(2019, APRIL, 14).time
        val end = GregorianCalendar(2019, JULY, 3).time
        val result = BusinessdayUtil.getNumberOfBusinessDays(start, end, fixedDateHolidays, inclusive = false)
        Assert.assertEquals(56, result)
    }

    @Test
    fun getNumberOfBusinessDays_moveToMondayHolidays() {
        val start = GregorianCalendar(2019, APRIL, 14).time
        val end = GregorianCalendar(2019, JULY, 4).time
        val result = BusinessdayUtil.getNumberOfBusinessDays(start, end, moveToMondayHolidays, inclusive = false)
        Assert.assertEquals(52, result)
    }

    @Test
    fun getNumberOfBusinessDays_certainDayHolidays() {
        val start = GregorianCalendar(2019, APRIL, 2).time
        val end = GregorianCalendar(2019, APRIL, 30).time
        val result = BusinessdayUtil.getNumberOfBusinessDays(start, end, certainDayHolidays, inclusive = false)
        Assert.assertEquals(16, result)
    }

    @Test
    fun getNumberOfBusinessDays_mixed() {
        val start = GregorianCalendar(2019, APRIL, 2).time
        val end = GregorianCalendar(2019, JULY, 4).time
        val result = BusinessdayUtil.getNumberOfBusinessDays(start, end, mixedHolidays, inclusive = false)
        Assert.assertEquals(61, result)
    }

    @Test
    fun testMock() {
        val holidays = Mocks.getNSWHolidays()
        val days = BusinessdayUtil.getNumberOfBusinessDays(
            Date().set(2019, JANUARY, 1),
            Date().set(2019, JUNE, 19),
            holidays,
            inclusive = true
        )
        Assert.assertEquals(116, days)
    }
}