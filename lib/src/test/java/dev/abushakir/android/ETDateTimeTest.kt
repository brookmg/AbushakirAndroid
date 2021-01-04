package dev.abushakir.android

import dev.abushakir.android.ETDateTime.Companion.dateToEpoch
import org.junit.Assert
import org.junit.Test
import java.util.*

class ETDateTimeTest {

    @Test fun `Parameterized Constructors`() {
        val etDateTime = ETDateTime(year =  2012, month = 7, day = 7)
        Assert.assertEquals(2012, etDateTime.year)
        Assert.assertEquals(7, etDateTime.month)
        Assert.assertEquals(7, etDateTime.day)
        Assert.assertEquals("፯", etDateTime.dayGeez());
    }

    @Test fun `Parameterized Constructors (year only)`() {
        val etDateTime = ETDateTime(year =  2010)
        Assert.assertEquals(2010, etDateTime.year)
        Assert.assertEquals(1, etDateTime.month)
        Assert.assertEquals(1, etDateTime.day)
        Assert.assertEquals("፩", etDateTime.dayGeez());
    }

    @Test fun `Named Constructors (parse)`() {
        val etDateTime = ETDateTime.parse("2012-07-07 15:12:17.500")
        Assert.assertEquals(1586218548118, dateToEpoch(2012, 7, 29, 0, 15, 48, 118))

        Assert.assertEquals("2012-07-07 15:12:17.500", etDateTime.toString());
        Assert.assertEquals(2012, etDateTime.year)
        Assert.assertEquals(7, etDateTime.month)
        Assert.assertEquals(7, etDateTime.day)
        Assert.assertEquals("፯", etDateTime.dayGeez())

        Assert.assertEquals(15, etDateTime.hour)
        Assert.assertEquals(12, etDateTime.minute)
        Assert.assertEquals(17, etDateTime.second)
        Assert.assertEquals(500, etDateTime.milliSecond)
    }

    @Test fun `Named Constructors (now)`() {
        val etDateTime = ETDateTime.now()
        Assert.assertEquals( Calendar.getInstance().get(Calendar.MINUTE), etDateTime.minute)
//        Assert.assertEquals( Calendar.getInstance().get(Calendar.SECOND), etDateTime.second)
    }

    @Test fun `Named Constructors (fromMillisecondsSinceEpoch)`() {
        val etDateTime = ETDateTime.fromMillisecondsSinceEpoch(1585731446021);
        Assert.assertEquals(etDateTime.toString(), "2012-07-23 08:57:26.021");
        Assert.assertEquals(2012, etDateTime.year)
        Assert.assertEquals(7, etDateTime.month)
        Assert.assertEquals(23, etDateTime.day)
        Assert.assertEquals("፳፫", etDateTime.dayGeez())

        Assert.assertEquals(8, etDateTime.hour)
        Assert.assertEquals(57, etDateTime.minute)
        Assert.assertEquals(26, etDateTime.second)
        Assert.assertEquals(21, etDateTime.milliSecond)
    }

    @Test fun `Testing functions`() {
        val ec = ETDateTime(year = 2012);
        Assert.assertEquals(true, ec.isAfter(ETDateTime(year = 2011)));
        Assert.assertEquals(true, ec.isBefore(ETDateTime(year = 2080)));

        Assert.assertEquals(true, ec.isAtSameMomentAs(ETDateTime(
                year = ec.year,
                month = ec.month,
                day =  ec.day,
                hour = ec.hour,
                minute = ec.minute,
                second = ec.second,
                milliSecond = ec.milliSecond)
        ));
    }
}