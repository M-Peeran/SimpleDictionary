package com.peeranm.simpledictionary.feature_word_meaning.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.peeranm.simpledictionary.feature_word_meaning.data.local.entity.WordInfoEntity
import com.peeranm.simpledictionary.core.Constants.Companion.DATABASE_NAME
import com.peeranm.simpledictionary.feature_word_meaning.utils.GsonParser

@Database(entities = [WordInfoEntity::class], version = 1)
@TypeConverters(MeaningConverters::class)
abstract class WordInfoDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordInfoDao

    companion object {

        @Volatile
        private var INSTANCE: WordInfoDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context): WordInfoDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = synchronized(lock) {
                    Room.databaseBuilder(
                        context,
                        WordInfoDatabase::class.java,
                        DATABASE_NAME
                    )
                        .addTypeConverter(MeaningConverters(GsonParser(Gson())))
                        .build() // Migration??
                }
                INSTANCE = instance
            }
            return instance
        }
    }
}