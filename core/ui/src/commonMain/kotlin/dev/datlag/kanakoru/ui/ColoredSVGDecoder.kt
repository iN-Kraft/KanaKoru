package dev.datlag.kanakoru.ui

import coil3.Extras
import coil3.ImageLoader
import coil3.decode.DecodeResult
import coil3.decode.Decoder
import coil3.decode.ImageSource
import coil3.fetch.SourceFetchResult
import coil3.getOrDefault
import coil3.request.Options
import coil3.svg.SvgDecoder
import okio.Buffer
import okio.use

internal class ColoredSVGDecoder(
    private val fetchResult: SourceFetchResult,
    private val options: Options
) : Decoder {

    override suspend fun decode(): DecodeResult? {
        val originalSVG = readOriginalSVG() ?: return null
        val mutatedSVG = mutateOrNull(originalSVG) ?: originalSVG

        return decodeMutatedSVG(mutatedSVG)
    }

    private fun readOriginalSVG(): String? {
        return fetchResult.source.sourceOrNull()?.use { it.readUtf8() }?.takeIf { it.isNotBlank() }
    }

    private fun mutateOrNull(svg: String): String? {
        val colors = options.extras.getOrDefault(colors)
        val regex = options.extras.getOrDefault(regex)

        if (colors.isEmpty() || regex.pattern.isEmpty()) {
            return null
        }

        var failed = false
        val result = regex.replace(svg) { matchResult ->
            if (failed) {
                return@replace matchResult.value
            }

            val index = matchResult.groupValues.getOrNull(1)?.toIntOrNull()?.minus(1)
            val runtimeColor = index?.let(colors::getOrNull)?.takeIf { it.isNotBlank() }
            val fallbackColor = matchResult.groupValues.getOrNull(2)?.takeIf { it.isNotBlank() }

            when {
                runtimeColor != null -> runtimeColor
                fallbackColor != null -> fallbackColor
                else -> {
                    failed = true
                    matchResult.value
                }
            }
        }

        return result.takeIf { !failed }
    }

    private suspend fun decodeMutatedSVG(svg: String): DecodeResult {
        val source = ImageSource(source = Buffer().writeUtf8(svg), fileSystem = options.fileSystem)

        return SvgDecoder(source = source, options = options).decode()
    }

    companion object Factory : Decoder.Factory {
        val colors: Extras.Key<List<String>> = Extras.Key(default = emptyList())
        val regex: Extras.Key<Regex> = Extras.Key(default = Regex(""))
        val defaultPlaceholderRegex = Regex("""var\(--placeholder-color-(\d+)(?:,\s*(#[0-9a-fA-F]{3,8}))?\)""")

        override fun create(
            result: SourceFetchResult,
            options: Options,
            imageLoader: ImageLoader
        ): Decoder {
            return ColoredSVGDecoder(result, options)
        }
    }
}