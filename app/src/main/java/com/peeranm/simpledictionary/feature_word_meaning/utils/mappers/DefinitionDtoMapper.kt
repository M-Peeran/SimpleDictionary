package com.peeranm.simpledictionary.feature_word_meaning.utils.mappers

import com.peeranm.simpledictionary.core.Mapper
import com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto.DefinitionDto
import com.peeranm.simpledictionary.feature_word_meaning.model.Definition

class DefinitionDtoMapper : Mapper<DefinitionDto, Definition> {

    override fun fromEntity(entity: DefinitionDto): Definition {
        return Definition(
            definition = entity.definition ?: "No definition found",
            example = entity.example ?: "No example found"
        )
    }

    override fun toEntity(model: Definition): DefinitionDto {
        return DefinitionDto(
            definition = model.definition,
            example = model.example
        )
    }
}