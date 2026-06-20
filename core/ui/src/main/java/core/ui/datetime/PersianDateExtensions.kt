package core.ui.datetime

import core.common.model.TimeSlot
import io.github.persiancalendar.calendar.CivilDate
import io.github.persiancalendar.calendar.PersianDate
import java.time.*
import java.time.format.DateTimeFormatter


/* ============================================================
   FORMATTERS
   ============================================================ */

private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
private val weeNames = listOf(
    "شنبه",
    "یکشنبه",
    "دوشنبه",
    "سه‌شنبه",
    "چهارشنبه",
    "پنجشنبه",
    "جمعه"
)

private val monthNames = listOf(
    "فروردین",
    "اردیبهشت",
    "خرداد",
    "تیر",
    "مرداد",
    "شهریور",
    "مهر",
    "آبان",
    "آذر",
    "دی",
    "بهمن",
    "اسفند"
)


/* ============================================================
   CONVERSION
   ============================================================ */

/* ---------- LocalDate → PersianDate ---------- */

fun LocalDate.toPersianDate(): PersianDate {
    val jdn = CivilDate(year, monthValue, dayOfMonth).toJdn()
    return PersianDate(jdn)
}


/* ---------- LocalDateTime → PersianDate ---------- */

fun LocalDateTime.toPersianDate(): PersianDate {
    val jdn = CivilDate(year, monthValue, dayOfMonth).toJdn()
    return PersianDate(jdn)
}


/* ---------- PersianDate → LocalDateTime ---------- */

fun PersianDate.toLocalDateTime(hour: Int, minute: Int): LocalDateTime {
    val civilDate = CivilDate(this.toJdn())
    return LocalDateTime.of(civilDate.year, civilDate.month, civilDate.dayOfMonth, hour, minute)
}


/* ============================================================
   PERSIAN FORMAT
   ============================================================ */
/*  11:20 */
fun LocalDateTime.toTimeText(): String = format(timeFormatter)

/* شنبه */
fun PersianDate.getWeekName(): String {
    val dayOfWeek = ((this.toJdn() + 2L) % 7L).toInt()
    return weeNames[dayOfWeek]
}

 /* اسفند */
fun PersianDate.getMonthName(): String =
    monthNames[this.month - 1]

/* شنبه */
fun LocalDate.toPersianWeekDay(): String =
    toPersianDate().getWeekName()

/* 29 بهمن */
fun LocalDate.toPersianDayTitle(): String {
    val persianDate = this.toPersianDate()
    return "${persianDate.dayOfMonth} ${persianDate.getMonthName()}"
}

fun LocalDateTime.toPersianDateTime (): String {
    val persianDate = this.toPersianDate()
    return "${persianDate.getWeekName()}, ${persianDate.dayOfMonth} ${persianDate.getMonthName()} - ${this.toTimeText()}"
}

/* ============================================================
   GROUPING FOR APPOINTMENT (NO FLOW / SUSPEND FRIENDLY)
   ============================================================ */

data class DaySlotsUi (
    val date: LocalDate,
    val week: String,
    val dayOfMonth: String,
    val slots: List<TimeSlot>
)
