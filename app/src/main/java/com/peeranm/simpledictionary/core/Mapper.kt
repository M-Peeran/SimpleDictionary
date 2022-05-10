package com.peeranm.simpledictionary.core

interface Mapper<Entity, Model> {
    fun fromEntity(entity: Entity): Model
    fun toEntity(model: Model): Entity
}