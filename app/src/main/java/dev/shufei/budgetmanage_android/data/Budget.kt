package dev.shufei.budgetmanage_android.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
data class Budget(
    @ColumnInfo(name = "id") @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "startDate") val startDate: String?,
    @ColumnInfo(name = "endDate") val endDate: String?,
    @ColumnInfo(name = "budgetAmount") val budgetAmount: Int = 0
) {
    val startDateLocalDate: LocalDate
        get() {
            val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            return LocalDate.parse(startDate)
        }

    val endDateLocalDate: LocalDate
        get() {
            val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            return LocalDate.parse(endDate)
        }
}
