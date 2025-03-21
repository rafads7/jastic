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
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.permissions.JasticPermission
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.saved_destinations.domain.SavedDestination
import com.rafaelduransaez.feature.saved_destinations.presentation.utils.toAnnotatedString

@Composable
fun SavedDestinationsScreen(
    savedDestinations: List<SavedDestination>,
    paddingValues: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    listState: LazyListState = rememberLazyListState(),
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(JasticTheme.size.minimum),
            state = listState
        ) {
            items(count = savedDestinations.size, key = { savedDestinations[it].alias }) { index ->
                SavedDestinationItem(
                    destination = savedDestinations[index],
                    onDestinationEditClicked = { savedDestination ->
                        onPermissionNeeded(JasticPermission.Location) {
                            onRouteTo(
                                NavigationGraphs.MapGraph(
                                    latitude = savedDestination.latitude,
                                    longitude = savedDestination.longitude,
                                    radiusInMeters = savedDestination.radiusInMeters
                                )
                            )
                        }
                    }, onDestinationClicked = {
                        //onRouteTo(SavedDestinationsRoutes.DestinationDetail(it))
                    }
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
fun SavedDestinationItem(
    destination: SavedDestination,
    onDestinationEditClicked: (SavedDestination) -> Unit,
    onDestinationClicked: (SavedDestination) -> Unit
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
