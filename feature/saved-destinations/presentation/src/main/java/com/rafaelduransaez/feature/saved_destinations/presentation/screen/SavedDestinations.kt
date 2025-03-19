package com.rafaelduransaez.feature.saved_destinations.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rafaelduransaez.core.components.jCard.JCard
import com.rafaelduransaez.core.components.jCard.JCardBody
import com.rafaelduransaez.core.components.jCard.JCardHeader
import com.rafaelduransaez.core.components.jCard.JCardIconAction
import com.rafaelduransaez.core.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.feature.saved_destinations.domain.SavedDestination
import com.rafaelduransaez.feature.saved_destinations.presentation.navigation.SavedDestinationsRoutes
import com.rafaelduransaez.feature.saved_destinations.presentation.utils.toAnnotatedString

@Composable
fun SavedDestinationsScreen(
    savedDestinations: List<SavedDestination>,
    paddingValues: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    listState: LazyListState = rememberLazyListState(),
    onRouteTo: NavRouteTo
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(JasticTheme.size.minimum),
            state = listState
        ) {
            items(count = savedDestinations.size, key = { savedDestinations[it].alias }) { index ->
                SavedDestinationItem(savedDestinations[index], { id -> })
            }
        }
        AddFAB(
            modifier = Modifier
                .padding(JasticTheme.size.large)
                .align(Alignment.BottomEnd)
        ) {
            onRouteTo(SavedDestinationsRoutes.Map)
        }
    }
}

@Composable
fun SavedDestinationItem(
    destination: SavedDestination,
    onDestinationClicked: (Int) -> Unit
) {
    JCard(
        onClick = { onDestinationClicked(destination.id) },
    ) {
        Box(Modifier.fillMaxSize()) {
            Column {
                JCardHeader(text = destination.alias)
                JCardBody(text = destination.address)
                JCardBody(text = destination.toAnnotatedString())
            }
            JCardIconAction(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Default.Edit
            ) {

            }
        }
    }
}
