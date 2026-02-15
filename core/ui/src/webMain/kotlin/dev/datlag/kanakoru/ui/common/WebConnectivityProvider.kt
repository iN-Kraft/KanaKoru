package dev.datlag.kanakoru.ui.common

import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityProvider
import kotlinx.browser.window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.w3c.dom.Navigator
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

class WebConnectivityProvider : ConnectivityProvider {
    override fun monitor(): Flow<Connectivity.Status> = callbackFlow {
        val navigator = window.navigator

        trySend(getStatus(navigator))

        val statusListener = object : EventListener {
            override fun handleEvent(event: Event) {
                trySend(getStatus(navigator))
            }
        }

        window.addEventListener("online", statusListener)
        window.addEventListener("offline", statusListener)

        val connection = (navigator as? NavigatorWithConnection)?.connection
        val connectionChangeListener: (Event) -> Unit = {
            trySend(getStatus(navigator))
        }

        connection?.addEventListener("change", connectionChangeListener)

        awaitClose {
            window.removeEventListener("online", statusListener)
            window.removeEventListener("offline", statusListener)
            connection?.removeEventListener("change", connectionChangeListener)
        }
    }

    private fun getStatus(navigator: Navigator): Connectivity.Status {
        if (!navigator.onLine) {
            return Connectivity.Status.Disconnected
        }

        val connection = (navigator as? NavigatorWithConnection)?.connection
            ?: return Connectivity.Status.Connected(metered = false)

        val isSaveData = connection.saveData == true
        val type = connection.type ?: "unknown"
        val isMetered = isSaveData || type == "cellular"

        return Connectivity.Status.Connected(metered = isMetered)
    }
}