package dev.datlag.kanakoru.ui.common

import org.w3c.dom.events.Event

external interface NetworkInformation {
    val saveData: Boolean?
    val type: String?
    val effectiveType: String?
    fun addEventListener(type: String, listener: (Event) -> Unit)
    fun removeEventListener(type: String, listener: (Event) -> Unit)
}