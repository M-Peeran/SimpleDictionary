package com.peeranm.simpledictionary.feature_word_meaning.use_cases


class WordInfoUseCases(
    val getWordInfo: GetWordInfoUseCase,
    val getAllWordInfos: GetAllWordInfosUseCase,
    val getWordInfoFromCache: GetWordInfoFromCacheUseCase
)