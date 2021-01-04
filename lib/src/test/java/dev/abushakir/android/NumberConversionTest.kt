package dev.abushakir.android

import dev.abushakir.android.util.Constants
import dev.abushakir.android.util.ConversionLogic
import org.junit.Assert
import org.junit.Test

class NumberConversionTest {

    @Test
    fun convert_1_to_10() {
        for (i in 1..10) {
            val result = ConversionLogic.convertToEthiopic(i)
            Assert.assertEquals(Constants.dayNumbers[i-1], result)
        }
    }

    @Test
    fun convert_11_to_100() {
        for (i in 11..30) {
            val result = ConversionLogic.convertToEthiopic(i)
            Assert.assertEquals(Constants.dayNumbers[i-1], result)
        }

        val result = ConversionLogic.convertToEthiopic(60)
        Assert.assertEquals("፷", result)
    }

}