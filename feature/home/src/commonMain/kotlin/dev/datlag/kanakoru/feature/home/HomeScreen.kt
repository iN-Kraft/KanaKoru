package dev.datlag.kanakoru.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.datlag.kanakoru.feature.home.resources.HomeRes
import dev.datlag.kanakoru.feature.home.resources.hiragana
import dev.datlag.kanakoru.feature.home.resources.hiragana_char
import dev.datlag.kanakoru.feature.home.resources.katakana
import dev.datlag.kanakoru.feature.home.resources.katakana_char
import dev.datlag.kanakoru.feature.home.resources.ready_to_learn
import dev.datlag.kanakoru.ui.common.merge
import dev.datlag.kanakoru.ui.common.plus
import dev.datlag.kanakoru.ui.svg.AsyncSVG
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.DI
import org.kodein.di.compose.localDI

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    di: DI = localDI(),
    viewModel: HomeViewModel = viewModel { HomeViewModel(di) },
    onHiraganaClick: () -> Unit,
    onKatakanaClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeFlexibleTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    val greeting by viewModel.greeting.collectAsState()

                    Text(text = stringResource(greeting))
                },
                subtitle = {
                    Text(text = stringResource(HomeRes.string.ready_to_learn))
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding.merge(PaddingValues(horizontal = 16.dp, vertical = 16.dp)).plus(PaddingValues(top = 32.dp)),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                val recommended by viewModel.recommendedHiragana.collectAsState(null)

                DeformableCard(
                    onClick = onHiraganaClick,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    cardColors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    circleRadius = 24.dp,
                    gap = 8.dp,
                    circleContent = {
                        IconButton(
                            onClick = onHiraganaClick,
                            modifier = Modifier.matchParentSize(),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = CircleShape
                        ) {
                            recommended?.let {
                                Text(
                                    text = it.value.toString(),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncSVG(
                            modifier = Modifier.fillMaxSize(),
                            model = if (isSystemInDarkTheme()) Image.dreamerDark else Image.dreamerLight,
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.FillWidth
                        )
                        Column(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8F))
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = stringResource(HomeRes.string.hiragana),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            item {
                val recommended by viewModel.recommendedKatakana.collectAsState(null)

                DeformableCard(
                    onClick = onKatakanaClick,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    cardColors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    circleRadius = 24.dp,
                    gap = 8.dp,
                    circleContent = {
                        IconButton(
                            onClick = onKatakanaClick,
                            modifier = Modifier.matchParentSize(),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            shape = CircleShape
                        ) {
                            recommended?.let {
                                Text(
                                    text = it.value.toString(),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncSVG(
                            modifier = Modifier.fillMaxSize(),
                            model = if (isSystemInDarkTheme()) Image.relaxingAtHomeDark else Image.relaxingAtHomeLight,
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.FillWidth
                        )
                        Column(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8F))
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = stringResource(HomeRes.string.katakana),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}