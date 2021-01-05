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

    @Test fun `general number converter test`() {
        val arabicNumberList = listOf(1, 10, 15, 20, 25, 78, 105, 333, 450, 600, 1000, 1001, 1010, 1056, 1200, 2013, 9999, 10000)
        val geezNumberList = listOf("፩", "፲", "፲፭", "፳", "፳፭", "፸፰", "፻፭", "፫፻፴፫", "፬፻፶", "፮፻", "፲፻", "፲፻፩", "፲፻፲", "፲፻፶፮", "፲፪፻", "፳፻፲፫", "፺፱፻፺፱", "፻፻")

        for (i in arabicNumberList.indices) {
            Assert.assertEquals(geezNumberList[i] , ConversionLogic.convertToEthiopic(arabicNumberList[i]))
        }
    }
}