package org.lotka.xenon.data.remote.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        if (value.isNullOrEmpty()) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Convert a List<String> to a JSON string
    @TypeConverter
    fun fromListString(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    // Convert Boolean to Integer
    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    // Convert Integer to Boolean
    @TypeConverter
    fun fromInt(value: Int): Boolean {
        return value != 0
    }
}

