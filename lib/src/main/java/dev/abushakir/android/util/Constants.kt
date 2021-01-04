package dev.abushakir.android.util

object Constants {
    // The 4 Evangelist(s)
    val evangelists = listOf("ዮሐንስ", "ማቴዎስ", "ማርቆስ", "ሉቃስ")

    // Before Christ (BC)
    const val ameteFeda = 5500

    // ancient values
    const val tinteAbekte = 11
    const val tinteMetkih = 19

    // weekdays
    val weekdays = listOf("ሰኞ", "ማግሰኞ", "ረቡዕ", "ሐሙስ", "አርብ", "ቅዳሜ", "እሁድ")

    // Weekday's Tewsak (corresponding numbers)
    val yeeletTewsak: Map<String, Int> = mapOf(
        "አርብ" to 2,
        "ሐሙስ" to 3,
        "ረቡዕ" to 4,
        "ማግሰኞ" to 5,
        "ሰኞ" to 6,
        "እሁድ" to 7,
        "ቅዳሜ" to 8
    )

    // Holiday's Tewsak (corresponding numbers)
    val _yebealTewsak: Map<String, Int> = mapOf(
        "ነነዌ" to 0,
        "ዓቢይ ጾም" to 14,
        "ደብረ ዘይት" to 41,
        "ሆሣዕና" to 62,
        "ስቅለት" to 67,
        "ትንሳኤ" to 69,
        "ርክበ ካህናት" to 93,
        "ዕርገት" to 108,
        "ጰራቅሊጦስ" to 118,
        "ጾመ ሐዋርያት" to 119,
        "ጾመ ድህነት" to 121,
    )

    val dayNumbers = listOf(
        "፩",
        "፪",
        "፫",
        "፬",
        "፭",
        "፮",
        "፯",
        "፰",
        "፱",
        "፲",
        "፲፩",
        "፲፪",
        "፲፫",
        "፲፬",
        "፲፭",
        "፲፮",
        "፲፯",
        "፲፰",
        "፲፱",
        "፳",
        "፳፩",
        "፳፪",
        "፳፫",
        "፳፬",
        "፳፭",
        "፳፮",
        "፳፯",
        "፳፰",
        "፳፱",
        "፴"
    )

    val months = listOf(
        "መስከረም",
        "ጥቅምት",
        "ኅዳር",
        "ታኅሳስ",
        "ጥር",
        "የካቲት",
        "መጋቢት",
        "ሚያዝያ",
        "ግንቦት",
        "ሰኔ",
        "ኃምሌ",
        "ነሐሴ",
        "ጷጉሜን"
    )

    const val maxMillisecondsSinceEpoch = 8640000000000000;

    const val ethiopicEpoch = 2796;
    const val unixEpoch = 719163;

    const val dayMilliSec = 86400000;
    const val hourMilliSec = 3600000;
    const val minMilliSec = 60000;
    const val secMilliSec = 1000;
    
    val geezNumbers: Map<Int, Char> = mapOf(
        1 to '፩',
        2 to '፪',
        3 to '፫',
        4 to '፬',
        5 to '፭',
        6 to '፮',
        7 to '፯',
        8 to '፰',
        9 to '፱',
        10 to '፲',
        20 to '፳',
        30 to '፴',
        40 to '፵',
        50 to '፶',
        60 to '፷',
        70 to '፸',
        80 to '፹',
        90 to '፺',
        100 to '፻',
        10000 to '፼'
    )
}