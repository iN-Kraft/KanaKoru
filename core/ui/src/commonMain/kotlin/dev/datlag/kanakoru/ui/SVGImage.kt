package dev.datlag.kanakoru.ui

import dev.datlag.inkraft.INKraft
import dev.datlag.kommons.cache.EvictionPolicy
import dev.datlag.kommons.cache.InMemoryCache

object SVGImage {

    private val pathCache = InMemoryCache<String, String>(
        maxSize = 100
    ) {
        evictionPolicy = EvictionPolicy.LRU
    }

    fun getUri(path: String, defaultResolver: (String) -> String): String {
        val cached = pathCache.tryGet(path)
        if (!cached.isNullOrBlank()) {
            return cached
        }

        val defaultUri = defaultResolver(path)
        if (!INKraft.Platform.isWeb) {
            pathCache.tryPut(path, defaultUri)
            return defaultUri
        }

        return defaultUri.replace("index\\.html\\./".toRegex(), "").also {
            pathCache.tryPut(path, it)
        }
    }
}