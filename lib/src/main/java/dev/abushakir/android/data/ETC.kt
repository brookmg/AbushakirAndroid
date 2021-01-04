package dev.abushakir.android.data

import dev.abushakir.android.ETDateTime
import dev.abushakir.android.error.MonthNumberException
import dev.abushakir.android.util.Constants
import dev.abushakir.android.util.Constants.dayNumbers
import dev.abushakir.android.util.Constants.months
import dev.abushakir.android.util.Constants.weekdays

/**
 * The Ethiopian calendar is one of the the calendars which uses the solar
 * system to reckon years, months and days, even time. Ethiopian single year
 * consists of 365.25 days which will be 366 days with in 4 years period which
 * causes the leap year. Ethiopian calendar has 13  months from which 12 months
 * are full 30 days each and the 13th month will be 5 days or 6 days during
 * leap year.
 *
 *
 * Create [ETC] object instances which are days of certain month in a certain
 * year, using one of the constructors.
 *
 * ```
 * ETC etc = ETC(year: 2012, month: 7, day: 4);
 * ```
 *
 * or
 *
 * ```
 * ETC today = ETC.today();
 * ```
 *
 * After creating instance of [ETC], you can navigate to the future or past
 * of the given date.
 *
 * ```
 * ETC _nextMonth = today.nextMonth;
 * ETC _prevMonth = today.prevMonth;
 * ```
 *
 * you can also get the same month of different year (the next year or
 * prev one)
 *
 * ```
 * ETC _nextYear = today.nextYear;
 * ETC _prevYear = today.prevYear;
 * ```
 *
 * All the available days within a single month can be found using
 *
 * ```
 * var monthDaysIter = today.monthDays();
 * ```
 *
 * or just all of the days available in the given year can also be found using
 * ```
 * var yearDaysIter = today.yearDays();
 * ```
 *
 */
@Suppress("SuspiciousVarProperty")
class ETC {

    lateinit var _date: ETDateTime
    var year: Int = 0
        get() = _date.year
    var month: Int = 0
        get() = _date.month
    var day: Int = 0
        get() = _date.day

    /**
    * Construct an [ETC] instance.
    *
    * For example. to create a new [ETC] object representing the 1st of ጷጉሜን 2011,
    *
    * ```
    * val etc = ETC(year= 2011, month= 13, day= 1);
    * ```
    *
    *  Month and day are optional, if not provided ETC will assume the first
    *  day of the first month of the given year.
    */
    constructor(year: Int, month: Int = 1, day: Int = 1) {
        this.year = year
        this.month = month
        this.day = day
        _date = ETDateTime(year, month, day, 0 , 0 , 0, 0, 0);
    }

    /**
    * Construct an [ETC] instance of the day the object is created.
    *
    * ```
    * var today = ETC.today();
    * ```
    */
    fun today() {
        _date = ETDateTime.now()
    }

    fun allMonths() = months

    fun dayNumbers() = Constants.dayNumbers

    fun weekDays() = weekdays

    fun monthName() = _date.monthGeez()

    fun nextMonth() = ETC(_date.year, month= _date.month + 1)

    fun prevMonth() = ETC(
        year= if (_date.month == 1) _date.year - 1 else _date.year,
        month= if(_date.month - 1 == 0) 13 else _date.month - 1
    )

    fun nextYear() = ETC(year= _date.year + 1, month= _date.month);

    fun prevYear() = ETC(year= _date.year - 1, month= _date.month);

    fun monthRange(): Pair<Int, Int> {
        if (_date.month !in 13 downTo 1) throw MonthNumberException();
        return _date.weekday() to if (_date.month == 13) { (if (_date.isLeapYear()) 6 else 5) } else 30;
    }

    data class MonthDayItem(val year: Int, val month: Int, val day: Int, val dayInGeez: String, val weekDayName: String, val monthBeginning: Int)

    fun monthDay(geezDay: Boolean = false, weekDayName: Boolean = false) : List<MonthDayItem> {
        val returnable: MutableList<MonthDayItem> = mutableListOf()
        var monthBeginning = monthRange().first;
        val daysInMonth = monthRange().second;

        for ( i in 0..daysInMonth) {
            returnable.add(MonthDayItem(_date.year, _date.month, _date.day, dayNumbers[i], weekdays[monthBeginning], monthBeginning))
            monthBeginning = (monthBeginning + 1) % 7;
        }
        return returnable
    }

    fun monthDays(year: Int, month: Int, geezDay: Boolean = false, weekDayName: Boolean = false) : List<MonthDayItem> {
        val returnable: MutableList<MonthDayItem> = mutableListOf()
        val yr = ETDateTime(year, month, 1, 0 , 0 , 0, 0, 0);
        var monthBeginning = yr.weekday();
        val daysInMonth = if (yr.month == 13) { if (yr.isLeapYear()) 6 else 5 } else 30

        for ( i in 0..daysInMonth) {
            returnable.add(MonthDayItem(_date.year, _date.month, _date.day, dayNumbers[i], weekdays[monthBeginning], monthBeginning))
            monthBeginning = (monthBeginning + 1) % 7;
        }
        return returnable
    }

    fun yearDays(geezDay: Boolean = false, weekDayName: Boolean = false) : List<List<MonthDayItem>> {
        val returnable: MutableList<List<MonthDayItem>> = mutableListOf()
        for (i in 0..months.size) {
            returnable.add(monthDays(_date.year, i + 1, geezDay= geezDay, weekDayName= weekDayName))
        }
        return returnable
    }

}