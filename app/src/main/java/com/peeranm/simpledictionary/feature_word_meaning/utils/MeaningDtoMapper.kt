package com.peeranm.simpledictionary.feature_word_meaning.utils

import com.peeranm.simpledictionary.core.Mapper
import com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto.MeaningDto
import com.peeranm.simpledictionary.feature_word_meaning.model.Meaning

class MeaningDtoMapper : Mapper<MeaningDto, Meaning> {

    private val definitionDtoMapper = DefinitionDtoMapper()

    override fun fromEntity(entity: MeaningDto): Meaning {
        return Meaning(
            definitions = entity.definitions?.map { definitionDtoMapper.fromEntity(it) } ?: emptyList(),
            partOfSpeech = entity.partOfSpeech ?: "No part of speech found"
        )
    }

    override fun toEntity(model: Meaning): MeaningDto {
        return MeaningDto(
            definitions = model.definitions.map { definitionDtoMapper.toEntity(it) },
            partOfSpeech = model.partOfSpeech
        )
    }
}