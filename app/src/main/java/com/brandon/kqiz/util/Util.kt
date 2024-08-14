package com.brandon.kqiz.util

import com.brandon.kqiz.R

const val QUESTION_CHUNK = 65
object Util {
    fun getRawResourceIds(): List<Int> {
        val rawResources = R.raw::class.java.fields
        return rawResources.mapNotNull { field ->
            try {
                field.getInt(null)
            } catch (e: Exception) {
                null
            }
        }
    }
}