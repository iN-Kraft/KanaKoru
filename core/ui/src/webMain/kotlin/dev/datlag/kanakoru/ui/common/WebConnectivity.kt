package dev.datlag.kanakoru.ui.common

import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class WebConnectivity(
    scope: CoroutineScope,
    options: ConnectivityOptions
) : Connectivity, CoroutineScope by scope {

    private var job: Job? = null

    private val _statusUpdates = MutableStateFlow<Connectivity.Status>(Connectivity.Status.Connected(metered = false))
    override val statusUpdates: SharedFlow<Connectivity.Status> = _statusUpdates.asSharedFlow()

    private val _monitoring = MutableStateFlow(false)
    override val monitoring: StateFlow<Boolean> = _monitoring.asStateFlow()

    init {
        if (options.autoStart) {
            start()
        }
    }

    override suspend fun status(): Connectivity.Status {
        return statusUpdates.firstOrNull() ?: _statusUpdates.value
    }

    override fun start() {
        job?.cancel()
        job = launch {
            _monitoring.update { true }
        }
    }

    override fun stop() {
        cancelJob()
        _monitoring.update { false }
    }

    private fun cancelJob() {
        cancel()
        job?.cancel()
        job = null
    }
}