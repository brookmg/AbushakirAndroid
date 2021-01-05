package dev.abushakir.android

import dev.abushakir.android.data.BahireHasab
import org.junit.Assert
import org.junit.Test

class BahireHasabTest {

    @Test fun `testing bahire hasab methods`() {
        val bh = BahireHasab(year = 2011)
        Assert.assertEquals(25, bh.abekte())
        Assert.assertEquals(5, bh.metkih())
        Assert.assertEquals("የካቲት" to 11 , bh.nenewe())
        Assert.assertEquals("የካቲት" to 11 , bh.getSingleBealOrTsom("ነነዌ"))
        Assert.assertEquals("የካቲት" to 25 , bh.getSingleBealOrTsom("ዓቢይ ጾም"))
        Assert.assertEquals("መጋቢት" to 22 , bh.getSingleBealOrTsom("ደብረ ዘይት"))
        Assert.assertEquals("ሚያዝያ" to 13 , bh.getSingleBealOrTsom("ሆሣዕና"))
        Assert.assertEquals("ሚያዝያ" to 18 , bh.getSingleBealOrTsom("ስቅለት"))
        Assert.assertEquals("ሚያዝያ" to 20 , bh.getSingleBealOrTsom("ትንሳኤ"))
        Assert.assertEquals("ግንቦት" to 14 , bh.getSingleBealOrTsom("ርክበ ካህናት"))
        Assert.assertEquals("ግንቦት" to 29 , bh.getSingleBealOrTsom("ዕርገት"))
        Assert.assertEquals("ሰኔ" to 9 , bh.getSingleBealOrTsom("ጰራቅሊጦስ"))
        Assert.assertEquals("ሰኔ" to 10 , bh.getSingleBealOrTsom("ጾመ ሐዋርያት"))
        Assert.assertEquals("ሰኔ" to 12 , bh.getSingleBealOrTsom("ጾመ ድህነት"))
        Assert.assertEquals(25, bh.abekte())
    }

}