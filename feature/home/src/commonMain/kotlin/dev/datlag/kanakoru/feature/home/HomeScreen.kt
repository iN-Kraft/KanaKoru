package dev.datlag.kanakoru.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.datlag.kanakoru.feature.home.resources.HomeRes
import dev.datlag.kanakoru.feature.home.resources.hiragana
import dev.datlag.kanakoru.feature.home.resources.hiragana_char
import dev.datlag.kanakoru.feature.home.resources.katakana
import dev.datlag.kanakoru.feature.home.resources.katakana_char
import dev.datlag.kanakoru.feature.home.resources.practise
import dev.datlag.kanakoru.feature.home.resources.ready_to_learn
import dev.datlag.kanakoru.ui.common.header
import dev.datlag.kanakoru.ui.common.merge
import dev.datlag.kanakoru.ui.common.plus
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
    onHiraganaClick: () -> Unit,
    onKatakanaClick: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding.merge(PaddingValues(horizontal = 16.dp, vertical = 16.dp)).plus(PaddingValues(top = 32.dp)),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            header {
                val greeting by viewModel.greeting.collectAsState()

                Text(
                    text = stringResource(greeting),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            header {
                Text(text = stringResource(HomeRes.string.ready_to_learn))
            }
            item {
                Card(
                    onClick = onHiraganaClick,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1F),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        text = stringResource(HomeRes.string.hiragana),
                        fontWeight = FontWeight.Medium
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1F).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            text = stringResource(HomeRes.string.hiragana_char),
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }
            item {
                Card(
                    onClick = onKatakanaClick,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1F),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        text = stringResource(HomeRes.string.katakana),
                        fontWeight = FontWeight.Medium
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1F).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            text = stringResource(HomeRes.string.katakana_char),
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }
        }
    }
}