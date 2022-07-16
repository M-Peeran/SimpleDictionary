package com.peeranm.simpledictionary.feature_word_meaning.utils.mappers

import com.peeranm.simpledictionary.core.Mapper
import com.peeranm.simpledictionary.feature_word_meaning.data.local.entity.WordInfoEntity
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo

class WordInfoEntityMapper : Mapper<WordInfoEntity, WordInfo> {

    override fun fromEntity(entity: WordInfoEntity): WordInfo {
        return WordInfo(
            id = entity.id,
            word = entity.word,
            meanings = entity.meanings,
            phonetic = entity.phonetic,
            audio = entity.audio
        )
    }

    override fun toEntity(model: WordInfo): WordInfoEntity {
        return WordInfoEntity(
            word = model.word,
            meanings = model.meanings,
            phonetic = model.phonetic,
            audio = model.audio
        )
    }

    fun toEntities(models: List<WordInfo>): List<WordInfoEntity> {
        return models.map { toEntity(it) }
    }

    fun fromEntities(entities: List<WordInfoEntity>): List<WordInfo> {
        return entities.map { fromEntity(it) }
    }

}