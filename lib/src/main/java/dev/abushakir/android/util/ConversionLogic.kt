package dev.abushakir.android.util

import dev.abushakir.android.error.EthiopicNumberException
import dev.abushakir.android.util.Constants.geezNumbers
import kotlin.math.truncate

object ConversionLogic {

    private fun divide(denominator: Int, nominator: Int) : Pair<Double, Int> {
        return truncate(nominator.toDouble() / denominator.toDouble()) to nominator % denominator
    }

    private fun convert1To10IntoET(number: Int) : String {
        if (number < 1) {
            throw  EthiopicNumberException("Zero (0) and Negative numbers doesn't exist in Ethiopic numerals");
        }

        return geezNumbers[number].toString()
    }

    private fun convert11To100IntoET(number: Int): String {
        return if (number == 100) {
            geezNumbers[number].toString()
        } else {
            val result = divide(10, number)
            if (result.second > 0) "${geezNumbers[result.first.toInt() * 10]}${geezNumbers[result.second]}" else "${geezNumbers[result.first.toInt() * 10]}"
        }
    }

    private fun convert101To1000IntoET(num: Int) : String {
        val result = divide(100, num)

        if (result.second == 0) {
            return "${geezNumbers[result.first.toInt()]}${geezNumbers[100]}"
        }

        val left = if (result.first.toInt() == 1) "${geezNumbers[100]}" else "${geezNumbers[result.first.toInt()]}${geezNumbers[100]}"
        val right = if (result.second <= 10) convert1To10IntoET(result.second) else convert11To100IntoET(result.second)

        return "$left$right"
    }

    fun convertToEthiopic(num: Int) : String {

        if (num < 1) {
            throw EthiopicNumberException("Zero (0) and Negative numbers doesn't exist in Ethiopic numerals");
        }

        return when (num) {
            in 1..10 -> convert1To10IntoET(num)
            in 11..100 -> convert11To100IntoET(num)
            in 101..1000 -> convert101To1000IntoET(num)
            in 1001..10000 -> {
                val result = divide(100, num)

                if (result.second == 0) {
                    return if (result.first.toInt() < 11) "${geezNumbers[result.first.toInt()]}${geezNumbers[100]}"
                    else "${convert11To100IntoET(result.first.toInt())}${geezNumbers[100]}";
                }

                val left = convert11To100IntoET(result.first.toInt());
                val right = if (result.second < 11) convert1To10IntoET(result.second) else convert11To100IntoET(result.second);

                "${left}${geezNumbers[100]}${right}";
            }
            else -> ""
        }
    }

}