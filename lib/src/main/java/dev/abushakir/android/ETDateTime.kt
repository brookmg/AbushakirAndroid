package dev.abushakir.android

import android.nfc.FormatException
import android.text.format.DateUtils
import dev.abushakir.android.util.Constants.ameteFeda
import dev.abushakir.android.util.Constants.dayMilliSec
import dev.abushakir.android.util.Constants.dayNumbers
import dev.abushakir.android.util.Constants.ethiopicEpoch
import dev.abushakir.android.util.Constants.hourMilliSec
import dev.abushakir.android.util.Constants.maxMillisecondsSinceEpoch
import dev.abushakir.android.util.Constants.minMilliSec
import dev.abushakir.android.util.Constants.months
import dev.abushakir.android.util.Constants.secMilliSec
import dev.abushakir.android.util.Constants.unixEpoch
import java.time.Duration
import java.util.*
import kotlin.math.abs
import kotlin.math.truncate

@Suppress("SuspiciousVarProperty", "unused")
class ETDateTime {

    /**
     * The year.
     *
     * ```
     * val covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
     * assert(covid19Confirmed.year == 2012);
     * ```
     */
    var year: Int = 0
        get() = truncate((4 * (fixed - ethiopicEpoch) + 1463).toDouble() / 1461).toInt()

    /**
    * The month [1..13].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(moonLanding.month == 7);
    * ```
    */
    var month: Int = 0
        get() = (truncate((fixed - fixedFromEthiopic(year, 1, 1)).toDouble() / 30) + 1).toInt()

    /**
    * The day of the month [1..30].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(covid19Confirmed.day == 4);
    * ```
    */
    var day: Int = 0
        get() = fixed + 1 - fixedFromEthiopic(year, month, 1)

    /**
    * The hour of the day, expressed as in a 24-hour clock [0..23].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(covid19Confirmed.hour == 13);
    * ```
    */
    var hour: Int = 0
        get() = truncate(moment / hourMilliSec.toDouble()).toInt() % 24

    /**
    * The minute [0...59].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(covid19Confirmed.minute == 18);
    * ```
    */
    var minute: Int = 0
        get() = truncate(moment / minMilliSec.toDouble()).toInt() % 60

    /**
    * The second [0...59].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(covid19Confirmed.second == 4);
    * ```
    */
    var second: Int = 0
        get() = truncate(moment / secMilliSec.toDouble()).toInt() % 60

    /**
    * The millisecond [0...999].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(covid19Confirmed.millisecond == 0);
    * ```
    */
    var milliSecond: Int = 0
        get() = (moment % 1000).toInt()

    var microSecond: Int = 0

    fun monthGeez() = months[(month - 1) % 13]

    /**
    * The day of the month in Ge'ez ['፩'..'፴'].
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * assert(covid19Confirmed.dayGeez == '፬');
    * ```
    */
    fun dayGeez() = dayNumbers[(day - 1) % 30]

    /**
    * The Date
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * var date = covid19Confirmed.date;
    * assert(date['year'] == 2012);
    * ```
    */
    fun date() : Map<String, Int> = mapOf(
            "year" to year,
            "month" to month,
            "day" to day
    )

    /**
    * The Time
    *
    * ```
    * var covid19Confirmed = EtDatetime.parse("2012-07-04 13:18:04Z");
    * var time = covid19Confirmed.time;
    * assert(time['hour'] == 13);
    * ```
    */
    fun time(): Map<String, Int> = mapOf(
            "h" to hour,
            "m" to minute,
            "s" to second
    )

    companion object {

        private val parseFormat =
            "^([+-]?\\d{4,6})-?(\\d\\d)-?(\\d\\d)(?:[ T](\\d\\d)(?::?(\\d\\d)(?::?(\\d\\d)(?:[.,](\\d+))?)?)?$( ?[zZ]| ?([-+])(\\d\\d)(?::?(\\d\\d))?)?)?$".toRegex()

        fun fourDigits(n: Int): String {
            val absN: Int = abs(n)
            val sign = if (n < 0) "-" else ""
            if (absN >= 1000) return "$n"
            if (absN >= 100) return "${sign}0$absN"
            return if (absN >= 10) "${sign}00$absN" else "${sign}000$absN"
        }

        fun sixDigits(n: Int): String? {
            if (n < -9999 || n > 9999) return null
            val absN: Int = abs(n)
            val sign = if (n < 0) "-" else "+"
            return if (absN >= 100000) "$sign$absN" else "${sign}0$absN"
        }

        fun threeDigits(n: Int): String {
            if (n >= 100) return "$n"
            return if (n >= 10) "0$n" else "00$n"
        }

        fun twoDigits(n: Int): String {
            return if (n >= 10) "$n" else "0$n"
        }

        private fun withValue(moment: Long, fixed: Int) : ETDateTime {
            return ETDateTime().apply {
                this.moment = moment
                this.fixed = fixed
                
                if (abs(Date().time) > maxMillisecondsSinceEpoch || (abs(Date().time) == maxMillisecondsSinceEpoch)) {
                    throw IllegalArgumentException("Calendar is outside valid range: ${abs(Date().time)}")
                }
            }
        }

        /**
        * Constructs a new [ETDateTime] instance based on [formattedString].
        *
        * The [formattedString] must not be `null`.
        * Throws a [FormatException] if the input string cannot be parsed.
        *
        * The function parses a subset of ISO 8601
        * which includes the subset accepted by RFC 3339.
        *
        * The accepted inputs are currently:
        *
        * * A date: A signed four-to-six digit year, two digit month and
        *   two digit day, optionally separated by `-` characters.
        *   Examples: "19700101", "-0004-12-24", "81030-04-01".
        * * An optional time part, separated from the date by either `T` or a space.
        *   The time part is a two digit hour,
        *   then optionally a two digit minutes value,
        *   then optionally a two digit seconds value, and
        *   then optionally a '.' or ',' followed by at least a one digit
        *   second fraction.
        *   The minutes and seconds may be separated from the previous parts by a
        *   ':'.
        *   Examples: "12", "12:30:24.124", "12:30:24,124", "123010.50".
        *
        * Examples of accepted strings:
        *
        * * `"2012-02-27 13:27:00"`
        * * `"2012-02-27 13:27:00.123456789z"`
        * * `"2012-02-27 13:27:00,123456789z"`
        * * `"20120227 13:27:00"`
        * * `"20120227T132700"`
        * * `"20120227"`
        * * `"+20120227"`
        * * `"2012-02-27T14Z"`
        * * `"2012-02-27T14+00:00"`
        * * `"-123450101 00:00:00 Z"`: in the year -12345.
        * * `"2002-02-27T14:00:00-0500"`: Same as `"2002-02-27T19:00:00Z"`
        */
        fun parse(formattedString: String) : ETDateTime {
            val re = parseFormat
            val match = (re.find(formattedString, 0))?.groupValues ?: listOf()
            if (match.size > 2) {
                fun parseIntOrZero(matched: String?): Int {
                    if (matched == null) return 0
                    return (matched.toInt())
                }

                fun parseMilliAndMicroseconds(matched: String?): Int {
                    if (matched == null) return 0
                    // val length = matched.length

                    var result = 0
                    for (i in 0..5) {
                        result *= 10
                        if (i < matched.length) {
                            result += matched.codePointAt(i) xor 0x30
                        }
                    }

                    return result
                }

                val years = match[1].toInt()
                val month = match[2].toInt()
                val day = match[3].toInt()
                
                val hour = parseIntOrZero(match[4])
                var minute = parseIntOrZero(match[5])
                val second = parseIntOrZero(match[6])
                
                val milliAndMicroseconds = parseMilliAndMicroseconds(match[7])
                val millisecond = truncate(milliAndMicroseconds / 1_000.toDouble()).toInt()

                if (match[8].isNotBlank()) {
                    
                    // timezone part
                    if (match[9].isNotBlank()) {
                        // timezone other than 'Z' and 'z'.
                        val sign = if (match[9] == "-") -1 else 1
                        val hourDifference = match[10].toInt()
                        var minuteDifference = parseIntOrZero(match[11])
                        minuteDifference += 60 * hourDifference
                        minute -= sign * minuteDifference
                    }
                }

                val value = dateToEpoch(years, month, day, hour, minute, second, millisecond)
                val fixedValue = fixedFromEthiopic(years, month, day)
                
                if (value == 0L) {
                    throw FormatException("Time out of range $formattedString")
                }
                
                return withValue(value, fixedValue)
            } else {
                throw FormatException("Invalid date format $formattedString")
            }
        }
        
        /**
        * Constructs an [ETDateTime] instance with current date and time.
        *
        * ```
        * val thisInstant = EtDatetime.now();
        * ```
        */
        fun now() : ETDateTime {
            return ETDateTime().apply {
                val dateTime = Date().time
                fixed = fixedFromUnix(dateTime)
                moment = dateTime
            }
        }

        /**
         * Constructs an [ETDateTime] instance
         * with the given [milliSecond].
         *
         * ```
         * val thisInstant = EtDatetime.fromMillisecondsSinceEpoch(1585742246021)
         * ```
         */
        fun fromMillisecondsSinceEpoch(milliSecond: Long): ETDateTime {
            return ETDateTime().apply {
                fixed = fixedFromUnix(milliSecond)
                moment = milliSecond

                if (abs(milliSecond) > maxMillisecondsSinceEpoch || (abs(milliSecond) == maxMillisecondsSinceEpoch)) {
                    throw IllegalArgumentException("Calendar is outside valid range: $milliSecond")
                }
            }
        }

        /**
         * Converts an ethiopic date to [fixed] date by adding the days elapsed to the last day before the [ethiopicEpoch].
         */
        fun fixedFromEthiopic(year: Int, month: Int, day: Int): Int {
            return (ethiopicEpoch - 1 + 365 * (year - 1) + truncate(year.toDouble() / 4) + 30 * (month - 1) + day).toInt()
        }

        /**
         * Returns [fixed] date from Unix [ms] count.
         */
        private fun fixedFromUnix(ms: Long) = (unixEpoch + truncate(ms.toDouble() / 86400000)).toInt()

        /**
        * Converts the given broken down date to millisecondsSinceEpoch.
        *
        * ```
        * val someTime = dateToEpoch(2012, 7, 29, 0, 15, 48, 118);
        * assert(someTime == 1586218548118);
        * ```
        */
        fun dateToEpoch(year: Int, month: Int, date: Int, hour: Int, minute: Int,
                        second: Int, millisecond: Int): Long {
            return (fixedFromEthiopic(year, month, date) - unixEpoch) * DateUtils.DAY_IN_MILLIS +
                    hour * DateUtils.HOUR_IN_MILLIS +
                    minute * DateUtils.MINUTE_IN_MILLIS +
                    second * DateUtils.SECOND_IN_MILLIS +
                    millisecond * 1
        }

    }

    /**
     * Milliseconds since [UNIX Epoch](https://en.wikipedia.org/wiki/Unix_time)
     * of this EtDatetime.
     */
    var moment: Long = 0L

    /**
     * Fixed date—elapsed days since the onset of Monday, January 1, 1970 (Gregorian)
     */
    var fixed: Int = 0

    constructor()

    constructor(year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0, milliSecond: Int = 0, microSecond: Int = 0) {
        this.year = year
        this.month = month
        this.day = day
        this.hour = hour
        this.minute = minute
        this.second = second
        this.microSecond = microSecond
        this.milliSecond = milliSecond

        fixed = fixedFromEthiopic(year, month, day)
        moment = dateToEpoch(year, month, day, hour, minute, second, milliSecond)

        if (fixed == 0) throw IllegalArgumentException()
    }

    fun isLeapYear(): Boolean = this.year % 4 == 3

    fun weekday() = (yearFirstDay() + ((month - 1) * 2)) % 7

    /**
    * Returns the first day of the year
    */
    fun yearFirstDay(): Int {
        val ameteAlem = ameteFeda + year
        val rabeet = truncate(ameteAlem.toDouble() / 4)
        return (ameteAlem + rabeet).toInt() % 7
    }

    /**
    * Returns an ISO-8601 full-precision extended format representation.
    *
    * The format is `yyyy-MM-ddTHH:mm:ss.mmm`
    * where:
    *
    * * `yyyy` is a, possibly negative, four digit representation of the year,
    *   if the year is in the range -9999 to 9999,
    *   otherwise it is a signed six digit representation of the year.
    * * `MM` is the month in the range 01 to 12,
    * * `dd` is the day of the month in the range 01 to 31,
    * * `HH` are hours in the range 00 to 23,
    * * `mm` are minutes in the range 00 to 59,
    * * `ss` are seconds in the range 00 to 59 (no leap seconds),
    * * `mmm` are milliseconds in the range 000 to 999, and
    *
    * The resulting string can be parsed back using [parse].
    */
    fun toIso8601String(): String {
        val y = if (year >= -9999 && year <= 9999) fourDigits(year) else sixDigits(year)
        val m = twoDigits(month)
        val d = twoDigits(day)
        val h = twoDigits(hour)
        val min = twoDigits(minute)
        val sec = twoDigits(second)
        val ms = threeDigits(milliSecond)
        return "$y-$m-${d}T$h:$min:$sec.$ms"
    }

    /**
    * Returns a human-readable string for this instance.
    * The resulting string can be parsed back using [parse].
    */
    override fun toString(): String {
        val y = fourDigits(year)
        val m = twoDigits(month)
        val d = twoDigits(day)
        val h = twoDigits(hour)
        val min = twoDigits(minute)
        val sec = twoDigits(second)
        val ms = threeDigits(milliSecond)
        return "$y-$m-$d $h:$min:$sec.$ms"
    }
    
    /**
    * Returns a [Duration] ms with the difference between this and other.
    *
    * ```
    * var berlinWallFell = EtDatetime(1989, EtDatetime.november, 9);
    * var dDay = EtDatetime(1944, EtDatetime.june, 6);
    *
    * Duration difference = berlinWallFell.difference(dDay);
    * assert(difference == (16592 * DateUtils.DAY_IN_MILLIS));
    * ```
    *
    * The difference is measured in seconds and fractions of seconds.
    * The difference above counts the number of fractional seconds between
    * midnight at the beginning of those dates.
    */
    fun difference(date: ETDateTime) = (fixed - date.fixed)
    
    /**
    * Returns a new [ETDateTime] with [duration] ms added to current instance.
    *
    * ```
    * var today = new EtDatetime.now();
    * var fiftyDaysFromNow = today.add(50 * DateUtils.DAY_IN_MILLIS);
    * ```
    *
    * Notice that the duration being added is actually 50 * 24 * 60 * 60
    * seconds. If the resulting `EtDatetime` has a different daylight saving offset
    * than `this`, then the result won't have the same time-of-day as `this`, and
    * may not even hit the calendar date 50 days later.
    *
    * Be careful when working with dates in local time.
    */
    fun add(duration: Long): ETDateTime = fromMillisecondsSinceEpoch(moment + duration)

    /**
     * Returns a new [ETDateTime] with [duration] ms subtracted from current instance.
     *
     * ```
     * var today = new EtDatetime.now();
     * var fiftyDaysAgo = today.subtract(50 * DateUtils.DAY_IN_MILLIS);
     * ```
     *
     * Notice that the duration being added is actually 50 * 24 * 60 * 60
     * seconds. If the resulting `ETDateTime` has a different daylight saving offset
     * than `this`, then the result won't have the same time-of-day as `this`, and
     * may not even hit the calendar date 50 days later.
     *
     * Be careful when working with dates in local time.
     */
    fun subtract(duration: Long): ETDateTime = fromMillisecondsSinceEpoch(moment - duration)

    /**
    * Returns true if current instance occurs before the [other].
    *
    * ```
    * var now = new EtDatetime.now();
    * var earlier = now.subtract(const Duration(seconds: 5));
    * assert(earlier.isBefore(now));
    * assert(!now.isBefore(now));
    * ```
    */
    fun isBefore(other: ETDateTime) = fixed < other.fixed && moment < other.moment

    /**
    * Returns true if current instance occurs after [other].
    *
    * ```
    * var now = new EtDatetime.now();
    * var later = now.add(const Duration(seconds: 5));
    * assert(later.isAfter(now));
    * assert(!now.isBefore(now));
    * ```
    */
    fun isAfter(other: ETDateTime): Boolean = fixed > other.fixed && moment > other.moment

    /**
    * Returns true if current instance occurs at the same moment as [other].
    *
    * ```
    * var now = new EtDatetime.now();
    * var later = now.add(const Duration(seconds: 5));
    * assert(!later.isAtSameMomentAs(now));
    * assert(now.isAtSameMomentAs(now));
    * ```
    */
    fun isAtSameMomentAs(other: ETDateTime) = fixed == other.fixed && moment == other.moment

    /**
    * Compares this EtDatetime object to [other],
    * returning zero if the values are equal.
    *
    * Returns a negative value if this EtDatetime [isBefore] [other]. It returns 0
    * if it [isAtSameMomentAs] [other], and returns a positive value otherwise
    * (when this [isAfter] [other]).
    */
    operator fun compareTo(other: ETDateTime): Int {
        return when {
            this.isBefore(other) -> -1
            this.isAtSameMomentAs(other) -> 0
            else -> 1
        }
    }

}