package com.kguard.googlecalendarclone

import android.annotation.SuppressLint
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @SuppressLint("SimpleDateFormat")
    fun getTitleText(day :CalendarDay):String
    {
        val Sdf = SimpleDateFormat("MMMM")
        val FSdf = SimpleDateFormat("MMM, yyyy")
        return if(day.year == CalendarDay.today().year) {
            upperFirst(Sdf.format(getDate(day)))
        }
        else {
            upperFirst(FSdf.format(getDate(day)))
        }
    }

    private fun getDate(cDay: CalendarDay): Date {
        val calendar = Calendar.getInstance()
        calendar.set(cDay.year, cDay.month - 1, cDay.day)
        return calendar.time
    }

    fun upperFirst(str: String) = str.substring(0, 1).uppercase() + str.substring(1)
}