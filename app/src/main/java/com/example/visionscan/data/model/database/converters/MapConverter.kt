package com.example.visionscan.data.model.database.converters

import androidx.room.TypeConverter

class MapConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, String> {
        return if (value.isEmpty()) emptyMap()
        else value.split(";").associate {
            val (key, value) = it.split("=")
            key to value
        }
    }

    @TypeConverter
    fun toString(map: Map<String, String>): String {
        return map.entries.joinToString(";") { "${it.key}=${it.value}" }
    }
}