package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
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
import dev.datlag.kanakoru.ui.common.calculateWindowSizeClass
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
        val chunkedChars = remember(type) {
            val allChars = when (type) {
                is Kana.Hiragana -> JapaneseChar.Hiragana.chars
                is Kana.Katakana -> JapaneseChar.Katakana.chars
            }
            val typeCompanion = when (type) {
                is Kana.Hiragana -> JapaneseChar.Hiragana
                is Kana.Katakana -> JapaneseChar.Katakana
            }

            allChars.flatMap { char ->
                if (char in listOf(typeCompanion.ya, typeCompanion.yu, typeCompanion.wa, typeCompanion.wo)) {
                    listOf(char, null)
                } else {
                    listOf(char)
                }
            }.chunked(5).map { it.toImmutableList() }.toImmutableList()
        }
        val windowSizeClass = calculateWindowSizeClass()
        val isLandscape = remember(windowSizeClass) {
            windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact
        }

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    KanaHeaderContent(type)
                }
                LazyColumn(
                    modifier = Modifier.weight(1.5F),
                    contentPadding = PaddingValues(vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(chunkedChars) { index, rowChars ->
                        val shape = getSegmentedShape(index, chunkedChars.size)
                        SplitRowContainer(
                            chars = rowChars,
                            shape = shape,
                            onKana = onKana
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = innerPadding.merge(PaddingValues(16.dp)),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                    ) {
                        KanaHeaderContent(type)
                    }
                }
                itemsIndexed(chunkedChars) { index, rowChars ->
                    val shape = getSegmentedShape(index, chunkedChars.size)
                    SplitRowContainer(
                        chars = rowChars,
                        shape = shape,
                        onKana = onKana
                    )
                }
            }
        }
    }
}