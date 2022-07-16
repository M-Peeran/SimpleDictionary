package com.peeranm.simpledictionary.feature_word_meaning.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peeranm.simpledictionary.feature_word_meaning.data.local.entity.WordInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entities: List<WordInfoEntity>)

    @Query("delete from word_info where word in(:words)")
    suspend fun deleteWordInfos(words: List<String>)

    @Query("select * from word_info where word like '%'|| :word ||'%'")
    suspend fun getWordInfos(word: String): List<WordInfoEntity>

    @Query("select * from word_info order by id desc")
    fun getAllWordInfos(): Flow<List<WordInfoEntity>>

    @Query("select * from word_info where id =:id")
    suspend fun getWordInfoById(id: Long): WordInfoEntity
}