package com.example.movieguideapp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTimeUtils {

    companion object {

        fun getYear(date: String, formatPattern: String): String {
            val dateFormatter = DateTimeFormatter.ofPattern(formatPattern)
            val currentLocalDate = LocalDate.parse(date, dateFormatter)
            return currentLocalDate.year.toString()
        }
    }
}