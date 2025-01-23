package com.rafaelduransaez.jastic.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelduransaez.domain.models.JasticDestination
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.MyJasticUiState
import com.rafaelduransaez.jastic.ui.MyJasticViewModel
import com.rafaelduransaez.jastic.ui.components.jButton.JButton
import com.rafaelduransaez.jastic.ui.components.common.ColumnItemsSpacer
import com.rafaelduransaez.jastic.ui.components.common.JasticProgressIndicator
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon
import com.rafaelduransaez.jastic.ui.components.jText.JTextCardBody
import com.rafaelduransaez.jastic.ui.components.jText.JTextCardTitle
import com.rafaelduransaez.jastic.ui.components.jText.JTextTitle
import com.rafaelduransaez.jastic.ui.theme.JasticTheme
import com.rafaelduransaez.jastic.ui.utils.Constants.FIRST_ITEM_INDEX
import com.rafaelduransaez.jastic.ui.utils.Constants.FIRST_ITEM_TO_SCROLL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyJasticScreen(
    viewModel: MyJasticViewModel = viewModel(),
    contentPadding: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    onJasticDestinationClicked: (id: Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val showScrollToTopButton by remember { derivedStateOf { listState.firstVisibleItemIndex > FIRST_ITEM_TO_SCROLL } }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = uiState) {
            is MyJasticUiState.Loading -> {
                JasticProgressIndicator()
            }

            is MyJasticUiState.ShowMyJasticDestinations -> {
                if (state.jasticPoints.isEmpty()) {
                    EmptyScreen()
                } else {
                    JasticDestinationsList(
                        jasticPoints = state.jasticPoints,
                        paddingValues = contentPadding,
                        showScrollToTopButton = showScrollToTopButton,
                        listState = listState,
                        onJasticDestinationClicked = onJasticDestinationClicked
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JIcon(
            icon = Icons.TwoTone.LocationOn,
            tint = JasticTheme.colorScheme.secondary,
            contentDescriptionResId = R.string.str_jastic_empty_state_title
        )
        ColumnItemsSpacer(JasticTheme.size.large)
        JTextTitle(textId = R.string.str_jastic_empty_state_title)
    }
}

@Composable
fun JasticDestinationsList(
    jasticPoints: List<JasticDestination>,
    paddingValues: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    showScrollToTopButton: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onJasticDestinationClicked: (id: Int) -> Unit
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(JasticTheme.size.minimum),
            state = listState
        ) {
            items(count = jasticPoints.size, key = { jasticPoints[it].title }) { index ->
                JasticDestinationCard(jasticPoints[index], onJasticDestinationClicked)
            }
        }

        AnimatedVisibility(showScrollToTopButton, modifier = Modifier.align(Alignment.TopCenter)) {
            ScrollToTopButton(coroutineScope, listState)
        }
    }
}

@Composable
fun BoxScope.ScrollToTopButton(
    coroutineScope: CoroutineScope,
    listState: LazyListState
) {
    JButton(
        modifier = Modifier.align(Alignment.TopCenter),
        textId = R.string.str_jastic_scroll_to_top
    ) {
        coroutineScope.launch {
            listState.animateScrollToItem(FIRST_ITEM_INDEX)
        }
    }
}


@Composable
fun JasticDestinationCard(
    destination: JasticDestination,
    onJasticDestinationClicked: (id: Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(vertical = JasticTheme.size.small)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small,
        onClick = { onJasticDestinationClicked(destination.id) },
        colors = CardDefaults.cardColors(
            containerColor = JasticTheme.colorScheme.onSecondary,
            contentColor = JasticTheme.colorScheme.secondary
        )

    ) {
        Column {
            JTextCardTitle(text = destination.title)
            JTextCardBody(text = destination.description)
        }
    }
}
