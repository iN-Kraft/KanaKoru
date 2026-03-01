package dev.datlag.kanakoru.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.datlag.kanakoru.feature.home.resources.HomeRes
import dev.datlag.kanakoru.feature.home.resources.greeting
import dev.datlag.kanakoru.feature.home.resources.greeting_evening
import dev.datlag.kanakoru.feature.home.resources.greeting_morning
import dev.datlag.kanakoru.kodein.directInstance
import dev.datlag.kanakoru.kodein.optionalInstance
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.repository.DrawingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.kodein.di.DI
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

class HomeViewModel(
    val drawingRepository: DrawingRepository
) : ViewModel() {

    constructor(di: DI) : this(
        drawingRepository = di.directInstance<DrawingRepository>()
    )

    private val _greeting = MutableStateFlow(HomeRes.string.greeting)
    val greeting = _greeting.asStateFlow()

    val recommendedHiragana: Flow<JapaneseChar?> by lazy {
        drawingRepository.recommendedHiragana
    }

    val recommendedKatakana: Flow<JapaneseChar?> by lazy {
        drawingRepository.recommendedKatakana
    }

    init {
        startGreeting()
    }

    private fun startGreeting() = viewModelScope.launch {
        while (isActive) {
            val current = Clock.System.now()
            updateGreeting(current)

            val delayDuration = calculateDelayUntilNextHour(current)
            delay(delayDuration)
        }
    }

    private fun updateGreeting(current: Instant) {
        val currentHour = current.toLocalDateTime(TimeZone.currentSystemDefault()).hour
        val message = when (currentHour) {
            in 5..11 -> HomeRes.string.greeting_morning
            in 12..16 -> HomeRes.string.greeting
            else -> HomeRes.string.greeting_evening
        }

        _greeting.update { message }
    }

    private fun calculateDelayUntilNextHour(current: Instant): Duration {
        val localDateTime = current.toLocalDateTime(TimeZone.currentSystemDefault())

        val minutesPassed = localDateTime.minute
        val minutesToWait = 60 - minutesPassed

        return minutesToWait.minutes + 2.seconds
    }

}