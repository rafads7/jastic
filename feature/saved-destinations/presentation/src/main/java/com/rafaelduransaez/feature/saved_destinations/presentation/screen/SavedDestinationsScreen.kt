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
import com.rafaelduransaez.core.components.common.JasticDatabaseError
import com.rafaelduransaez.core.components.common.JasticEmptyScreen
import com.rafaelduransaez.core.components.common.JasticProgressIndicator
import com.rafaelduransaez.core.components.common.ObserveAsEvent
import com.rafaelduransaez.core.components.jCard.JCard
import com.rafaelduransaez.core.components.jCard.JCardBody
import com.rafaelduransaez.core.components.jCard.JCardHeader
import com.rafaelduransaez.core.components.jCard.JCardIconAction
import com.rafaelduransaez.core.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.permissions.JasticPermission
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI
import com.rafaelduransaez.feature.saved_destinations.presentation.R
import com.rafaelduransaez.feature.saved_destinations.presentation.utils.toAnnotatedString
import kotlinx.coroutines.flow.Flow

@Composable
fun SavedDestinationsScreen(
    uiState: SavedDestinationsUiState,
    navState: Flow<SavedDestinationsNavState>,
    onUiEvent: (SavedDestinationsUiEvent) -> Unit,
    paddingValues: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded,
) {
    ObserveAsEvent(flow = navState, key1 = true) {
        when (it) {
            SavedDestinationsNavState.Idle -> Unit
            SavedDestinationsNavState.Back -> onRouteTo(Back)
            is SavedDestinationsNavState.ToNewJasticPoint -> Unit
            is SavedDestinationsNavState.ToSavedDestinationsEdit ->
                onPermissionNeeded(JasticPermission.Location) {
                    onRouteTo(NavigationGraphs.MapGraph(id = it.destinationId))
                }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState) {
            SavedDestinationsUiState.Error -> JasticDatabaseError()
            SavedDestinationsUiState.Loading -> JasticProgressIndicator()
            is SavedDestinationsUiState.Success -> {
                if (uiState.savedDestinations.isEmpty())
                    JasticEmptyScreen(textId = R.string.str_feature_saved_destinations_empty_screen)
                else
                    SavedDestinationsList(
                        items = uiState.savedDestinations,
                        paddingValues = paddingValues,
                        onUiEvent = onUiEvent
                    )
            }
        }
        AddFAB(
            modifier = Modifier
                .padding(JasticTheme.size.large)
                .align(Alignment.BottomEnd)
        ) {
            onPermissionNeeded(JasticPermission.Location) {
                onRouteTo(NavigationGraphs.MapGraph())
            }
        }
    }
}

@Composable
private fun SavedDestinationsList(
    items: List<DestinationsListItemUI>,
    paddingValues: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    listState: LazyListState = rememberLazyListState(),
    onUiEvent: (SavedDestinationsUiEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(JasticTheme.size.minimum),
        state = listState
    ) {
        items(count = items.size, key = { items[it].alias }) { index ->
            SavedDestinationItem(
                destination = items[index],
                onDestinationEditClicked = { destination ->
                    onUiEvent(SavedDestinationsUiEvent.OnEditDestinationClick(destination.id))
                },
                onDestinationClicked = {
                    onUiEvent(SavedDestinationsUiEvent.OnDestinationClick(it.id))
                }
            )
        }
    }
}

@Composable
fun SavedDestinationItem(
    destination: DestinationsListItemUI,
    onDestinationEditClicked: (DestinationsListItemUI) -> Unit,
    onDestinationClicked: (DestinationsListItemUI) -> Unit
) {
    JCard(
        onClick = { onDestinationClicked(destination) },
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
                onDestinationEditClicked(destination)
            }
        }
    }
}
