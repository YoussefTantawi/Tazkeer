package com.tantawi.tazkeer.helpers

import android.content.Context
import android.text.format.DateFormat
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.database.TaskEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Helper keeps repeated date, time, and display mapping logic organized.
object DateTimeHelper {
    const val CATEGORY_STUDY = "Study"
    const val CATEGORY_WORK = "Work"
    const val CATEGORY_FAMILY = "Family"
    const val CATEGORY_PERSONAL = "Personal"
    const val CATEGORY_HEALTH = "Health"
    const val CATEGORY_FITNESS = "Fitness"
    const val CATEGORY_OTHER = "Others"
    const val CATEGORY_PRAYER = "Prayer"
    const val CATEGORY_AZKAR = "Azkar"

    const val LEGACY_CATEGORY_GYM = "Gym"
    const val LEGACY_CATEGORY_PRAYERS = "Prayers"
    const val LEGACY_CATEGORY_OTHER = "Other"

    const val PRIORITY_HIGH = "High"
    const val PRIORITY_MEDIUM = "Medium"
    const val PRIORITY_LOW = "Low"

    const val RECURRENCE_ONCE = "Once"
    const val RECURRENCE_DAILY = "Daily"
    const val RECURRENCE_CUSTOM = "Custom"

    const val DAY_SATURDAY = "SATURDAY"
    const val DAY_SUNDAY = "SUNDAY"
    const val DAY_MONDAY = "MONDAY"
    const val DAY_TUESDAY = "TUESDAY"
    const val DAY_WEDNESDAY = "WEDNESDAY"
    const val DAY_THURSDAY = "THURSDAY"
    const val DAY_FRIDAY = "FRIDAY"

    val dayOrder = listOf(
        DAY_SATURDAY,
        DAY_SUNDAY,
        DAY_MONDAY,
        DAY_TUESDAY,
        DAY_WEDNESDAY,
        DAY_THURSDAY,
        DAY_FRIDAY
    )

    val categoryOrder = listOf(
        CATEGORY_WORK,
        CATEGORY_STUDY,
        CATEGORY_PRAYER,
        CATEGORY_AZKAR,
        CATEGORY_FAMILY,
        CATEGORY_PERSONAL,
        CATEGORY_HEALTH,
        CATEGORY_FITNESS,
        CATEGORY_OTHER
    )

    fun todayBounds(): Pair<Long, Long> {
        val start = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val end = start.clone() as Calendar
        end.add(Calendar.DAY_OF_MONTH, 1)
        end.add(Calendar.MILLISECOND, -1)
        return start.timeInMillis to end.timeInMillis
    }

    fun apiDate(timeMillis: Long = System.currentTimeMillis()): String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(timeMillis))
    }

    fun localDateKey(timeMillis: Long = System.currentTimeMillis()): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date(timeMillis))
    }

    fun displayTime(context: Context, timeMillis: Long): String {
        val pattern = if (PreferencesHelper.use24HourPrayerTime(context)) "HH:mm" else "h:mm a"
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(timeMillis))
    }

    fun displayDateHeader(context: Context, timeMillis: Long): String {
        return DateFormat.getMediumDateFormat(context).format(Date(timeMillis))
    }

    fun stripApiTime(rawTime: String): String {
        return rawTime.trim().take(5)
    }

    fun prayerTimeMillis(date: String, time: String): Long {
        return try {
            SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).parse("$date ${stripApiTime(time)}")?.time ?: 0L
        } catch (_: Exception) {
            0L
        }
    }

    fun nextTimeMillis(hour: Int, minute: Int, recurrenceType: String, selectedDaysCsv: String): Long {
        return findNextTimeMillis(hour, minute, recurrenceType, selectedDaysCsv, System.currentTimeMillis())
    }

    fun nextOccurrenceAfter(task: TaskEntity, afterMillis: Long): Long {
        val calendar = Calendar.getInstance().apply { timeInMillis = task.timeMillis }
        return findNextTimeMillis(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            task.recurrenceType,
            task.selectedDaysCsv,
            afterMillis + 60_000L
        )
    }

    private fun findNextTimeMillis(
        hour: Int,
        minute: Int,
        recurrenceType: String,
        selectedDaysCsv: String,
        baseMillis: Long
    ): Long {
        val base = Calendar.getInstance().apply { timeInMillis = baseMillis }
        for (offset in 0..14) {
            val candidate = base.clone() as Calendar
            candidate.add(Calendar.DAY_OF_YEAR, offset)
            candidate.set(Calendar.HOUR_OF_DAY, hour)
            candidate.set(Calendar.MINUTE, minute)
            candidate.set(Calendar.SECOND, 0)
            candidate.set(Calendar.MILLISECOND, 0)

            val allowed = when (recurrenceType) {
                RECURRENCE_DAILY -> true
                RECURRENCE_CUSTOM -> selectedDays(selectedDaysCsv).contains(dayConstant(candidate))
                else -> offset <= 1
            }

            if (allowed && candidate.timeInMillis > baseMillis) {
                return candidate.timeInMillis
            }
        }
        return baseMillis + 24L * 60L * 60L * 1000L
    }

    fun shouldShowTaskToday(task: TaskEntity): Boolean {
        if (task.isSystemTask) return task.taskDate == localDateKey()

        val today = dayConstant(Calendar.getInstance())
        return when (task.recurrenceType) {
            RECURRENCE_DAILY -> true
            RECURRENCE_CUSTOM -> selectedDays(task.selectedDaysCsv).contains(today)
            else -> {
                val (start, end) = todayBounds()
                task.timeMillis in start..end
            }
        }
    }

    fun selectedDays(csv: String): Set<String> {
        if (csv == "ALL") return dayOrder.toSet()
        if (csv.isBlank()) return emptySet()
        return csv.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toSet()
    }

    fun selectedDaysCsv(days: Set<String>): String {
        return if (days.size == dayOrder.size) "ALL" else dayOrder.filter { days.contains(it) }.joinToString(",")
    }

    fun dayConstant(calendar: Calendar): String {
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SATURDAY -> DAY_SATURDAY
            Calendar.SUNDAY -> DAY_SUNDAY
            Calendar.MONDAY -> DAY_MONDAY
            Calendar.TUESDAY -> DAY_TUESDAY
            Calendar.WEDNESDAY -> DAY_WEDNESDAY
            Calendar.THURSDAY -> DAY_THURSDAY
            else -> DAY_FRIDAY
        }
    }

    fun localizedCategory(context: Context, category: String, customCategory: String? = null): String {
        val normalizedCategory = normalizeCategory(category)
        if (normalizedCategory == CATEGORY_OTHER && !customCategory.isNullOrBlank()) return customCategory
        return when (normalizedCategory) {
            CATEGORY_WORK -> context.getString(R.string.category_work)
            CATEGORY_STUDY -> context.getString(R.string.category_study)
            CATEGORY_PRAYER -> context.getString(R.string.category_prayer)
            CATEGORY_AZKAR -> context.getString(R.string.category_azkar)
            CATEGORY_FAMILY -> context.getString(R.string.category_family)
            CATEGORY_PERSONAL -> context.getString(R.string.category_personal)
            CATEGORY_HEALTH -> context.getString(R.string.category_health)
            CATEGORY_FITNESS -> context.getString(R.string.category_fitness)
            else -> context.getString(R.string.category_other)
        }
    }

    fun normalizeCategory(category: String): String {
        return when (category.trim()) {
            CATEGORY_PRAYER, LEGACY_CATEGORY_PRAYERS -> CATEGORY_PRAYER
            CATEGORY_FITNESS, LEGACY_CATEGORY_GYM -> CATEGORY_FITNESS
            CATEGORY_OTHER, LEGACY_CATEGORY_OTHER -> CATEGORY_OTHER
            CATEGORY_WORK -> CATEGORY_WORK
            CATEGORY_STUDY -> CATEGORY_STUDY
            CATEGORY_AZKAR -> CATEGORY_AZKAR
            CATEGORY_FAMILY -> CATEGORY_FAMILY
            CATEGORY_PERSONAL -> CATEGORY_PERSONAL
            CATEGORY_HEALTH -> CATEGORY_HEALTH
            else -> CATEGORY_OTHER
        }
    }

    fun isProtectedCategory(category: String): Boolean {
        val normalizedCategory = normalizeCategory(category)
        return normalizedCategory == CATEGORY_PRAYER || normalizedCategory == CATEGORY_AZKAR
    }

    fun categoryIconRes(category: String): Int {
        return when (normalizeCategory(category)) {
            CATEGORY_WORK -> R.drawable.ic_category_work
            CATEGORY_STUDY -> R.drawable.ic_category_study
            CATEGORY_PRAYER -> R.drawable.ic_category_prayer
            CATEGORY_AZKAR -> R.drawable.ic_category_azkar
            CATEGORY_FAMILY -> R.drawable.ic_category_family
            CATEGORY_PERSONAL -> R.drawable.ic_category_personal
            CATEGORY_HEALTH -> R.drawable.ic_category_health
            CATEGORY_FITNESS -> R.drawable.ic_category_fitness
            else -> R.drawable.ic_category_others
        }
    }

    fun localizedPriority(context: Context, priority: String): String {
        return when (priority) {
            PRIORITY_HIGH -> context.getString(R.string.priority_high)
            PRIORITY_MEDIUM -> context.getString(R.string.priority_medium)
            else -> context.getString(R.string.priority_low)
        }
    }

    fun localizedRecurrence(context: Context, recurrence: String): String {
        return when (recurrence) {
            RECURRENCE_DAILY -> context.getString(R.string.recurrence_daily)
            RECURRENCE_CUSTOM -> context.getString(R.string.recurrence_custom)
            else -> context.getString(R.string.recurrence_once)
        }
    }

    fun localizedTaskTitle(context: Context, task: TaskEntity): String {
        return localizedTaskTitle(context, task.title, task.category, task.systemTaskType)
    }

    fun localizedTaskTitle(
        context: Context,
        title: String,
        category: String,
        systemTaskType: String? = null
    ): String {
        val prayerName = systemTaskType
            ?.takeIf { it.startsWith("Prayer:") }
            ?.removePrefix("Prayer:")
            ?: title.takeIf { normalizeCategory(category) == CATEGORY_PRAYER }

        canonicalPrayerName(prayerName)?.let { return localizedPrayerName(context, it) }

        if (normalizeCategory(category) == CATEGORY_AZKAR || systemTaskType?.startsWith("Azkar:") == true) {
            return localizedAzkarTitle(context, title, systemTaskType)
        }

        return title
    }

    fun localizedPrayerName(context: Context, prayerName: String): String {
        return when (canonicalPrayerName(prayerName)) {
            "Fajr" -> context.getString(R.string.prayer_fajr)
            "Dhuhr" -> context.getString(R.string.prayer_dhuhr)
            "Asr" -> context.getString(R.string.prayer_asr)
            "Maghrib" -> context.getString(R.string.prayer_maghrib)
            "Isha" -> context.getString(R.string.prayer_isha)
            else -> prayerName.ifBlank { context.getString(R.string.prayer_sunrise) }
        }
    }

    fun canonicalPrayerName(prayerName: String?): String? {
        return when (prayerName?.trim()) {
            "Fajr", "الفجر" -> "Fajr"
            "Dhuhr", "الظهر" -> "Dhuhr"
            "Asr", "العصر" -> "Asr"
            "Maghrib", "المغرب" -> "Maghrib"
            "Isha", "العشاء" -> "Isha"
            else -> null
        }
    }

    fun countdownText(context: Context, targetMillis: Long, nowMillis: Long = System.currentTimeMillis()): String {
        val remainingMillis = targetMillis - nowMillis
        if (remainingMillis <= 0L) return context.getString(R.string.countdown_now)

        val totalMinutes = (remainingMillis + 59_999L) / 60_000L
        val hours = totalMinutes / 60L
        val minutes = totalMinutes % 60L

        return when {
            hours > 0L && minutes > 0L -> context.getString(R.string.countdown_hours_minutes, hours, minutes)
            hours > 0L -> context.getString(R.string.countdown_hours, hours)
            else -> context.getString(R.string.countdown_minutes, minutes)
        }
    }

    private fun localizedAzkarTitle(context: Context, title: String, systemTaskType: String?): String {
        return when (systemTaskType ?: title.trim()) {
            "Azkar:Morning", "Morning Azkar", "أذكار الصباح" -> context.getString(R.string.azkar_morning)
            "Azkar:Evening", "Evening Azkar", "أذكار المساء" -> context.getString(R.string.azkar_evening)
            "Azkar:Sleep", "Sleep Azkar", "أذكار النوم" -> context.getString(R.string.azkar_sleep)
            "Azkar:AfterPrayer", "After Prayer Azkar", "أذكار بعد الصلاة" -> context.getString(R.string.azkar_after_prayer)
            else -> title
        }
    }
}
