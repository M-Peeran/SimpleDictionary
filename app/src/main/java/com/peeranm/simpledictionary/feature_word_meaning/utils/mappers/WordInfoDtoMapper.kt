package com.peeranm.simpledictionary.feature_word_meaning.utils.mappers

import com.peeranm.simpledictionary.core.Mapper
import com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto.PhoneticDto
import com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto.WordInfoDto
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo

class WordInfoDtoMapper : Mapper<WordInfoDto, WordInfo> {

    private val meaningDtoMapper = MeaningDtoMapper()

    override fun fromEntity(entity: WordInfoDto): WordInfo {
        val phonetic = entity.phonetics?.firstOrNull()
        return WordInfo(
            word = entity.word ?: "No word found",
            meanings = entity.meanings?.map { meaningDtoMapper.fromEntity(it) } ?: emptyList(),
            phonetic = entity.phonetic ?: "No phonetic found",
            audio = phonetic?.audio ?: "No audio found"
        )
    }

    override fun toEntity(model: WordInfo): WordInfoDto {
        return WordInfoDto(
            meanings = model.meanings.map { meaningDtoMapper.toEntity(it) },
            phonetic = model.phonetic,
            word = model.word,
            phonetics = listOf(PhoneticDto(model.audio))
        )
    }

    fun fromEntities(entities: List<WordInfoDto>): List<WordInfo> {
        return entities.map { fromEntity(it) }
    }

    fun toEntities(models: List<WordInfo>): List<WordInfoDto> {
        return models.map { toEntity(it) }
    }
}