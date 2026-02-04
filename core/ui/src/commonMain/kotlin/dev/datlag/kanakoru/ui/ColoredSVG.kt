package dev.datlag.kanakoru.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import dev.datlag.kanakoru.ui.common.rememberDefaultColors
import dev.datlag.kanakoru.ui.common.toHexString
import dev.datlag.kanakoru.ui.module.UIModule
import org.kodein.di.compose.localDI
import org.kodein.di.compose.rememberInstance

@Composable
fun ColoredSVG(
    model: Any?,
    contentDescription: String?,
    colors: List<Color> = rememberDefaultColors(),
    placeholderRegex: Regex = ColoredSVGDecoder.defaultPlaceholderRegex,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = null,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    clipToBounds: Boolean = true,
) = with(localDI()) {
    val platformContext by rememberInstance<PlatformContext>()
    val imageLoader by rememberInstance<ImageLoader>(tag = UIModule.SVG_IMAGE_LOADER)
    val colorsAsStrings: List<String> = remember(colors) { colors.map { it.toHexString() } }
    val imageRequest =
        remember(model, platformContext, colorsAsStrings, placeholderRegex) {
            ImageRequest.Builder(platformContext)
                .data(model)
                .apply {
                    extras[ColoredSVGDecoder.colors] = colorsAsStrings
                    extras[ColoredSVGDecoder.regex] = placeholderRegex
                }
                .build()
        }

    AsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = modifier,
        placeholder = placeholder,
        error = error,
        fallback = fallback,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        clipToBounds = clipToBounds,
    )
}