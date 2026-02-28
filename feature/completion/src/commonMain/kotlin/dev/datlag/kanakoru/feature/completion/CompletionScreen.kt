package dev.datlag.kanakoru.feature.completion

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Verified
import dev.datlag.kanakoru.feature.completion.resources.CompletionRes
import dev.datlag.kanakoru.feature.completion.resources.done
import dev.datlag.kanakoru.feature.completion.resources.mastered_subtitle
import dev.datlag.kanakoru.feature.completion.resources.mastered_title
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.common.calculateWindowSizeClass
import dev.datlag.kanakoru.ui.svg.AsyncSVG
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CompletionScreen(
    japaneseChar: JapaneseChar,
    onFinish: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val windowSizeClass = calculateWindowSizeClass()
        val isLandscape = remember(windowSizeClass) {
            windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact
        }
        val isDark = isSystemInDarkTheme()
        val image = retain(isDark) {
            Image.random(isDark)
        }

        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1F),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncSVG(
                        modifier = Modifier.fillMaxWidth(0.6F),
                        model = image,
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
                ) {
                    Text(
                        text = stringResource(CompletionRes.string.mastered_title),
                        style = MaterialTheme.typography.headlineMediumEmphasized,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = stringResource(CompletionRes.string.mastered_subtitle))
                    Button(
                        onClick = onFinish,
                        modifier = Modifier.fillMaxWidth(0.8F)
                    ) {
                        Icon(
                            imageVector = MaterialSymbols.Rounded.Verified,
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = stringResource(CompletionRes.string.done))
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
            ) {
                AsyncSVG(
                    modifier = Modifier.fillMaxWidth(0.6F),
                    model = image,
                    contentDescription = null
                )
                Text(
                    text = stringResource(CompletionRes.string.mastered_title),
                    style = MaterialTheme.typography.headlineMediumEmphasized,
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(CompletionRes.string.mastered_subtitle))
                Button(
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth(0.7F)
                ) {
                    Icon(
                        imageVector = MaterialSymbols.Rounded.Verified,
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(CompletionRes.string.done))
                }
            }
        }
    }
}