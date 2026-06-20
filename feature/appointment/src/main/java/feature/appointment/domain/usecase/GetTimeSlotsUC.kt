package feature.appointment.domain.usecase

import core.ui.datetime.DaySlotsUi
import core.ui.datetime.toPersianDayTitle
import core.ui.datetime.toPersianWeekDay
import core.ui.datetime.toTimeText
import feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject
import kotlin.collections.component1

class GetTimeSlotsUC @Inject constructor(
    private val repo: AppointmentRepository
) {
    suspend operator fun invoke(id: Long): List<DaySlotsUi> =
        repo.getSlots(id)
            .groupBy { it.dateTime.toLocalDate() }
            .toSortedMap()
            .map { (date, items) ->
                DaySlotsUi(
                    date = date,
                    week = date.toPersianWeekDay(),
                    dayOfMonth = date.toPersianDayTitle(),
                    slots = items.sortedBy { it.dateTime }
                )
            }
            .filter { it.slots.isNotEmpty() }
            .sortedBy { it.date }
}