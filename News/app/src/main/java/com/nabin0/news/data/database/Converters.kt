package com.nabin0.news.data.database

import androidx.room.TypeConverter
import com.nabin0.news.data.model.Source

class Converters {

    @TypeConverter
    fun fromSourceToString(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun fromStringToSource(string: String): Source {
        return Source(string, string)
    }
}