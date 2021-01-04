package dev.abushakir.android.data

import android.util.Log
import dev.abushakir.android.ETDateTime
import dev.abushakir.android.error.BealNameException
import dev.abushakir.android.util.Constants
import dev.abushakir.android.util.Constants._yebealTewsak
import dev.abushakir.android.util.Constants.evangelists
import dev.abushakir.android.util.Constants.months
import dev.abushakir.android.util.Constants.tinteAbekte
import dev.abushakir.android.util.Constants.tinteMetkih
import dev.abushakir.android.util.Constants.weekdays
import dev.abushakir.android.util.Constants.yeeletTewsak
import kotlin.math.truncate


class BahireHasab {

    var year: Int = -1

    constructor(year: Int) {
        if (year < 0) this.year = ETDateTime.now().year else this.year = year;
    }

    fun getEvangelist(returnName: Boolean = false) : String {
        val evangelist = ameteAlem() % 4;
        if (returnName) {
            return evangelists[evangelist];
        }
        return evangelist.toString();
    }

    fun ameteAlem() = Constants.ameteFeda + this.year

    fun getMeskeremOne(returnName: Boolean = false) : String {
        val rabeet = truncate(ameteAlem().toDouble() / 4).toInt()
        val result = (ameteAlem() + rabeet) % 7;
        if (returnName) return weekdays[result];
        return result.toString();
    }

    fun metkih() = if (wenber() == 0) 30 else (wenber() * tinteMetkih) % 30

    fun wenber() = if (((ameteAlem() % 19) - 1) < 0) 0 else (ameteAlem() % 19) - 1

    fun abekte() = (wenber() * tinteAbekte) % 30

    fun yebealeMetkihWer() = if (metkih() > 14) 1 else 2

    fun nenewe() : Pair<String, Int> {
        val meskerem1 = getMeskeremOne(returnName = true)
        val month = yebealeMetkihWer()

        val date: Int
        var dayTewsak = 0;

        yeeletTewsak.forEach { el ->
            if (el.key == weekdays[(weekdays.indexOf(meskerem1) + metkih() - 1) % 7]) dayTewsak = el.value
        }

        var monthName = if (dayTewsak + metkih() > 30) "የካቲት" else "ጥር";

        if (month == 2) {
            // ጥቅምት
            monthName = "የካቲት";
            val tikimt1 = weekdays[(weekdays.indexOf(meskerem1) + 2) % 7]
            val metkihElet = weekdays[(weekdays.indexOf(tikimt1) + metkih() - 1) % 7]

            yeeletTewsak.forEach { el ->
                if (el.key == weekdays[weekdays.indexOf(metkihElet)]) dayTewsak = el.value
            }
        }

        date = metkih() + dayTewsak;
        return monthName to if (date % 30 == 0) 30 else date % 30;
    }

    data class BealDateItem(val beal: String, val month: String, val date: Int)

    fun allAtSwamat() : List<BealDateItem> {
        val mebajaHamer = nenewe();
        val result = mutableListOf<BealDateItem>();

        _yebealTewsak.forEach{ (beal, numOfDays) ->
            result.add(
                    BealDateItem(
                            beal = beal,
                            month = months[months.indexOf(mebajaHamer.first) + truncate((mebajaHamer.second + numOfDays).toDouble() / 30).toInt()],
                            date = if ((mebajaHamer.second + numOfDays) % 30 == 0) 30 else (mebajaHamer.second + numOfDays) % 30
                    )
            )
        }
        return result;
    }

    fun isMovableHoliday(holidayName: String?) : Boolean {
        return _yebealTewsak.keys.contains(holidayName)
    }

    fun getSingleBealOrTsom(name: String) : Pair<String, Int>? {
        try {
            val status = isMovableHoliday(name)
            if (status) {
                val mebajaHamer = nenewe();
                val target = _yebealTewsak[name] ?: 0
                return months[months.indexOf(mebajaHamer.first) + (truncate((mebajaHamer.second + target).toDouble() / 30)).toInt()] to
                if ((mebajaHamer.second + target) % 30 == 0) 30 else (mebajaHamer.second + target) % 30

            }
        } catch (e: Exception) {
            Log.e("::GetStringBealOrTsom" , e.toString());
        }
        return null
    }

}