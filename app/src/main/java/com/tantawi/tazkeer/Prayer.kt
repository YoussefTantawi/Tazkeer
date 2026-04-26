package quicktype

data class Prayer (
    val code: Long? = null,
    val status: String? = null,
    val data: Data? = null
)

data class Data (
    val timings: Timings? = null,
    val date: Date? = null,
    val meta: Meta? = null
)

data class Date (
    val readable: String? = null,
    val timestamp: String? = null,
    val hijri: Hijri? = null,
    val gregorian: Gregorian? = null
)

data class Gregorian (
    val date: String? = null,
    val format: String? = null,
    val day: String? = null,
    val weekday: GregorianWeekday? = null,
    val month: GregorianMonth? = null,
    val year: String? = null,
    val designation: Designation? = null,
    val lunarSighting: Boolean? = null
)

data class Designation (
    val abbreviated: String? = null,
    val expanded: String? = null
)

data class GregorianMonth (
    val number: Long? = null,
    val en: String? = null
)

data class GregorianWeekday (
    val en: String? = null
)

data class Hijri (
    val date: String? = null,
    val format: String? = null,
    val day: String? = null,
    val weekday: HijriWeekday? = null,
    val month: HijriMonth? = null,
    val year: String? = null,
    val designation: Designation? = null,
    val holidays: List<Any?>? = null,
    val adjustedHolidays: List<Any?>? = null,
    val method: String? = null
)

data class HijriMonth (
    val number: Long? = null,
    val en: String? = null,
    val ar: String? = null,
    val days: Long? = null
)

data class HijriWeekday (
    val en: String? = null,
    val ar: String? = null
)

data class Meta (
    val latitude: Long? = null,
    val longitude: Long? = null,
    val timezone: String? = null,
    val method: Method? = null,
    val latitudeAdjustmentMethod: String? = null,
    val midnightMode: String? = null,
    val school: String? = null,
    val offset: Map<String, Long>? = null
)

data class Method (
    val id: Long? = null,
    val name: String? = null,
    val params: Params? = null,
    val location: Location? = null
)

data class Location (
    val latitude: Double? = null,
    val longitude: Double? = null
)

data class Params (
    val fajr: Long? = null,
    val isha: Long? = null
)

data class Timings (
    val fajr: String? = null,
    val sunrise: String? = null,
    val dhuhr: String? = null,
    val asr: String? = null,
    val sunset: String? = null,
    val maghrib: String? = null,
    val isha: String? = null,
    val imsak: String? = null,
    val midnight: String? = null,
    val firstthird: String? = null,
    val lastthird: String? = null
)
