package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.feature.kana.resources.KanaRes
import dev.datlag.kanakoru.feature.kana.resources.description_hiragana
import dev.datlag.kanakoru.feature.kana.resources.description_katakana
import dev.datlag.kanakoru.ui.svg.AsyncSVG
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun KanaHeaderContent(type: Kana) {
    AsyncSVG(
        modifier = Modifier.fillMaxWidth(0.6F),
        model = when (type) {
            is Kana.Hiragana -> Image.workout()
            is Kana.Katakana -> Image.hiking()
        },
        contentDescription = null
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = when (type) {
            is Kana.Hiragana -> stringResource(KanaRes.string.description_hiragana)
            is Kana.Katakana -> stringResource(KanaRes.string.description_katakana)
        },
        textAlign = TextAlign.Center
    )
}