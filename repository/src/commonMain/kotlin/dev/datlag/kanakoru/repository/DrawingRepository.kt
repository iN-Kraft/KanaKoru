package dev.datlag.kanakoru.repository

import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.coroutines.flow.Flow
import dev.datlag.kanakoru.repository.local.DrawingRepository as LocalDBRepo

class DrawingRepository(
    val local: LocalDBRepo
) {

    val recommendedHiragana: Flow<JapaneseChar> by lazy {
        local.recommendedHiragana
    }
    val recommendedKatakana: Flow<JapaneseChar> by lazy {
        local.recommendedKatakana
    }
}