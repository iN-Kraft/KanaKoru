package dev.datlag.kanakoru.feature.kana

import dev.datlag.inkraft.INKraft
import dev.datlag.kanakoru.feature.kana.resources.KanaRes

internal object Image {

    val workInProgress by lazy {
        getUri("files/undraw_work-in-progress.svg")
    }

    val aroundTheWorld by lazy {
        getUri("files/undraw_around-the-world.svg")
    }

    private fun getUri(path: String): String {
        val defaultUri = KanaRes.getUri(path)

        if (!INKraft.Platform.isWeb) {
            return defaultUri
        }

        return defaultUri.replace("index\\.html\\./".toRegex(), "")
    }

}