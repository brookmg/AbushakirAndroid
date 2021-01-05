# Abushakir (ባሕረ ሃሳብ) [ Android ]
[![GitHub stars](https://img.shields.io/github/stars/brookmg/AbushakirAndroid?style=social)](https://github.com/brookmg/AbushakirAndroid/stargazers)       [![GitHub forks](https://img.shields.io/github/forks/brookmg/AbushakirAndroid?style=social)](https://github.com/brookmg/AbushakirAndroid/network)
[![GitHub license](https://img.shields.io/github/license/Nabute/Abushakir)](https://github.com/Nabute/Abushakir/blob/master/LICENSE)
![Android CI](https://github.com/brookmg/AbushakirAndroid/workflows/Android%20CI/badge.svg?branch=master)

The words Bahire Hasab originate from the ancient language of Ge'ez, ( Arabic: Abu Shakir) is a
time-tracking method, devised by the 12th pope of Alexandria, Pope St. Dimitri.

## This project is alpha mode! 

## What does it mean?

"Bahire Hasab /'bəhrɛ həsəb'/  " simply means "An age with a descriptive and chronological number". In some books it can also be found as "Hasabe Bahir", in a sense giving time an analogy, resembling a sea.

This package allows developers to implement Ethiopian Calendar and Datetime System in their application(s)`.

This package is implemented using the [UNIX EPOCH](https://en.wikipedia.org/wiki/Unix_time) which
means it's not a conversion of any other calendar system into Ethiopian, for instance, Gregorian Calendar.

Unix Epoch is measured using milliseconds since 01 Jan, 1970 UTC. In unix epoch leap seconds are ignored.

## Prerequisites

- Nothing so far ... 

## Getting started

* make sure to add jitpack to your repositories

```gradle 
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

* implement this lib ( latest version => [![](https://jitpack.io/v/brookmg/AbushakirAndroid.svg)](https://jitpack.io/#brookmg/AbushakirAndroid))

```gradle 
    dependencies {
        implementation 'com.github.brookmg:abushakirandroid:0.1.0-alpha1'
    }
```

## Example

```kotlin
   /**
   * Ethiopian Datetime Module [ETDateTime]
   */
  val now = ETDateTime.now() // => 2012-07-28 17:18:31.466
  print(now.date()) // => {year: 2012, month: 7, day: 28}
  print(now.time()) // => {h: 17, m: 18, s: 31}

  val covidFirstConfirmed = ETDateTime(year = 2012, month = 7, day = 4)
  val covidFirstConfirmedEpoch = ETDateTime.fromMillisecondsSinceEpoch(covidFirstConfirmed.moment)

  ETDateTime covidFirstDeath = ETDateTime.parse("2012-07-26 23:00:00")

  // Comparison of two ETDateTime Instances
  Duration daysWithOutDeath = covidFirstConfirmed.difference(covidFirstDeath)

  Assert.assertEquals(daysWithOutDeath.inDays() == -22, true) // 22 days
  Assert.assertEquals(covidFirstDeath.isAfter(covidFirstConfirmed), true)
  Assert.assertEquals(covidFirstDeath.isBefore(now), true)
  Assert.assertEquals(covidFirstConfirmed.isAtSameMomentAs(covidFirstConfirmedEpoch), true)

  /**
   * Ethiopian Calendar Module [ETC]
   */
  val ethiopianCalendar = ETC(year = 2012, month = 7, day = 4)

  //
  print(ethiopianCalendar.monthDays(geezDay = true, weekDayName = true)) // List of day detail in a month
  print(ethiopianCalendar.monthDays()[0]) // 👀


  print(ethiopianCalendar.nextMonth()) // => ETC instance of nextMonth, same year
  print(ethiopianCalendar.prevYear()) // => ETC instance of prevYear, same month

  /**
   * Bahire Hasab Module [BahireHasab]
   */
  val bh = BahireHasab(year = 2011)
//  val bh = BahireHasab() // Get's the current year

  print(bh.getEvangelist(returnName = true)) // => ሉቃስ

  print(bh.getSingleBealOrTsom("ትንሳኤ")) // ሚያዝያ to 20 ( Pair of month to day ) 

  bh.allAtswamat() // => List of All fasting and Movable holidays

  /**
   * Arabic or English number (1,2,3...) to Ethiopic or GE'EZ (፩, ፪, ፫...) number Converter
   */

  var input= [1, 10, 15, 20, 25, 78, 105, 333, 450, 600, 1000, 1001, 1010, 1056, 1200, 2013, 9999, 10000]

  for (var arabic in input) {
     print(ConversionLogic.convertToEthiopic(arabic)) // [፩, ፲, ፲፭, ፳, ፳፭, ፸፰, ፻፭, ፫፻፴፫, ፬፻፶, ፮፻, ፲፻, ፲፻፩, ፲፻፲, ፲፻፶፮, ፲፪፻, ፳፻፲፫, ፺፱፻፺፱, ፻፻]
  }

```

## Contributors

Thanks to the following people who have contributed to this project:

* [@Nabute](https://github.com/Nabute) 📖
* [@Nahom](https://github.com/icnahom) 📖
* [@BrookMG](https://github.com/brookmg) 📖

<!---You might want to consider using something like the [All Contributors](https://github.com/all-contributors/all-contributors) specification and its [emoji key](https://allcontributors.org/docs/en/emoji-key).--->

## Contact

If you want to contact me you can reach me at <daniel@ibrave.dev>.

## License
<!--- If you're not sure which open license to use see https://choosealicense.com/--->

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details
