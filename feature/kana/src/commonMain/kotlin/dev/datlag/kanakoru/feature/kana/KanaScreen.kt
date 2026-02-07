package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_back_ios_new
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.feature.kana.resources.KanaRes
import dev.datlag.kanakoru.feature.kana.resources.description_hiragana
import dev.datlag.kanakoru.feature.kana.resources.description_katakana
import dev.datlag.kanakoru.feature.kana.resources.topbar_hiragana
import dev.datlag.kanakoru.feature.kana.resources.topbar_katakana
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.ColoredSVG
import dev.datlag.kanakoru.ui.common.header
import dev.datlag.kanakoru.ui.common.merge
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun KanaScreen(
    type: Kana,
    onBack: () -> Unit,
    onKana: (JapaneseChar) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = MaterialSymbols.Rounded.Arrow_back_ios_new,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(text = when (type) {
                        is Kana.Hiragana -> stringResource(KanaRes.string.topbar_hiragana)
                        is Kana.Katakana -> stringResource(KanaRes.string.topbar_katakana)
                    })
                }
            )
        }
    ) { innerPadding ->
        val chars = remember(type) {
            when (type) {
                is Kana.Hiragana -> JapaneseChar.Hiragana.chars
                is Kana.Katakana -> JapaneseChar.Katakana.chars
            }.toImmutableList()
        }

        LazyVerticalGrid(
            columns = GridCells.FixedSize(60.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding.merge(PaddingValues(16.dp)),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            header {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val background = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.surfaceBright
                    } else {
                        MaterialTheme.colorScheme.surfaceDim
                    }

                    ColoredSVG(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(MaterialShapes.Cookie12Sided.toShape())
                            .background(background),
                        model = when (type) {
                            is Kana.Hiragana -> Image.workInProgress
                            is Kana.Katakana -> Image.aroundTheWorld
                        },
                        contentDescription = null,
                        fallback = rememberAsyncImagePainter(
                            Image.workInProgress
                        ),
                        onError = { state ->
                            state.result.throwable.printStackTrace()
                        }
                    )
                }
            }
            header {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = when (type) {
                        is Kana.Hiragana -> stringResource(KanaRes.string.description_hiragana)
                        is Kana.Katakana -> stringResource(KanaRes.string.description_katakana)
                    },
                    textAlign = TextAlign.Center
                )
            }
            items(chars, key = { it.value + it.romaji }) { char ->
                ElevatedCard(
                    onClick = {
                        onKana(char)
                    },
                    modifier = Modifier.fillMaxWidth().aspectRatio(1F),
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1F))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = char.value.toString(),
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = char.romaji,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}