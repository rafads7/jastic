package com.rafaelduransaez.feature.myjastic.presentation.jasticPoint

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rafaelduransaez.core.components.common.JasticDatabaseError
import com.rafaelduransaez.core.components.common.JasticProgressIndicator
import com.rafaelduransaez.core.components.common.ObserveAsEvent
import com.rafaelduransaez.core.components.jButton.JSaveAndGoButton
import com.rafaelduransaez.core.components.jButton.JSaveButton
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextField
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextFieldWithIconButton
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.permissions.JasticPermission
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.AliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.DestinationAliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.DestinationIconClicked
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.DestinationSavingOptionsChanged
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.MessageUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.Save
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.SaveAndGo
import kotlinx.coroutines.flow.Flow

@Composable
internal fun JasticPointDetailScreen(
    uiState: JasticPointDetailUiState,
    navState: Flow<JasticPointDetailNavState>,
    onUiEvent: (JasticPointDetailUserEvent) -> Unit,
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded,
    contentPadding: PaddingValues = PaddingValues(all = JasticTheme.size.normal)
) {

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { contactUri: Uri? ->
            contactUri?.let {
                val contactIdentifier = contactUri.toString()
                onUiEvent(JasticPointDetailUserEvent.ContactSelected(contactIdentifier))
            }
        }
    )

    NavigationHandler(navState, onRouteTo, onPermissionNeeded)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        if (uiState.error) {
            JasticDatabaseError()
        } else {

            if (uiState.isLoading) {
                JasticProgressIndicator(modifier = Modifier.fillMaxSize())
            }

            Column {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Alias(
                        alias = uiState.jasticPointAlias,
                        onAliasChanges = { onUiEvent(AliasUpdate(it)) }
                    )
                    Destination(
                        destinationAddress = uiState.geofenceLocation.address,
                        destinationAlias = uiState.destinationAlias,
                        onIconClick = { onUiEvent(DestinationIconClicked) },
                        onDestinationAliasChanged = { onUiEvent(DestinationAliasUpdate(it)) },
                        showSavingOptions = uiState.showDestinationSavingOptions,
                        savingSelectedOption = uiState.destinationSavingOptions,
                        onSavingDestinationOptionsChanged = {
                            onUiEvent(DestinationSavingOptionsChanged(it))
                        }
                    )
                    ContactPhoneNumber(
                        contactPhoneNumber = uiState.contactPhoneNumber,
                        onIconClick = {
                            onPermissionNeeded(JasticPermission.Contacts) {
                                contactPickerLauncher.launch(null)
                            }
                        }
                    )
                    Message(
                        message = uiState.jasticPointMessage,
                        onMessageChanges = { onUiEvent(MessageUpdate(it)) }
                    )
                }
                JSaveButton(enabled = uiState.isSaveEnabled) { onUiEvent(Save) }
                JSaveAndGoButton { onUiEvent(SaveAndGo) }
            }
        }
    }
}

@Composable
fun Alias(alias: String, onAliasChanges: (String) -> Unit) {
    JOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = JasticTheme.size.extraSmall),
        text = alias,
        hint = R.string.str_feature_myjastic_destiny_point_alias,
        onValueChange = onAliasChanges
    )
}

@Composable
fun Destination(
    destinationAddress: String,
    destinationAlias: String,
    showSavingOptions: Boolean,
    savingSelectedOption: DestinationSavingOptions,
    onSavingDestinationOptionsChanged: (DestinationSavingOptions) -> Unit,
    onIconClick: () -> Unit,
    onDestinationAliasChanged: (String) -> Unit
) {

    JOutlinedTextFieldWithIconButton(
        text = destinationAddress,
        onIconClick = onIconClick,
        icon = Icons.Default.LocationOn,
        hint = R.string.str_feature_myjastic_location,
        placeHolder = R.string.str_feature_myjastic_location_placeholder,
        readOnly = true
    )

    if (showSavingOptions) {
        DestinationSaveOptions(selectedOption = savingSelectedOption) {
            onSavingDestinationOptionsChanged(it)
        }
    }

    JOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = JasticTheme.size.extraSmall),
        text = destinationAlias,
        hint = R.string.str_feature_myjastic_location_alias,
        onValueChange = onDestinationAliasChanged
    )

}

@Composable
fun DestinationSaveOptions(
    selectedOption: DestinationSavingOptions,
    onOptionSelected: (DestinationSavingOptions) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = JasticTheme.size.extraSmall)
                .clickable { onOptionSelected(DestinationSavingOptions.Save) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == DestinationSavingOptions.Save,
                onClick = null, // Clicks handled on the whole Row
                colors = RadioButtonDefaults.colors(
                    selectedColor = JasticTheme.colorScheme.secondaryContainer,
                    unselectedColor = JasticTheme.colorScheme.secondary
                )
            )
            JText(
                modifier = Modifier.padding(start = JasticTheme.size.extraSmall),
                textId = R.string.str_feature_myjastic_save_new_location
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = JasticTheme.size.extraSmall)
                .clickable { onOptionSelected(DestinationSavingOptions.Update) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == DestinationSavingOptions.Update,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = JasticTheme.colorScheme.secondaryContainer,
                    unselectedColor = JasticTheme.colorScheme.secondary
                )
            )
            JText(
                modifier = Modifier.padding(start = JasticTheme.size.extraSmall),
                textId = R.string.str_feature_myjastic_update_current_location)
        }
    }
}


@Composable
fun Message(message: String, onMessageChanges: (String) -> Unit) {
    JOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = JasticTheme.size.extraSmall),
        text = message,
        hint = R.string.str_feature_myjastic_message,
        onValueChange = onMessageChanges
    )
}

@Composable
fun ContactPhoneNumber(contactPhoneNumber: String, onIconClick: () -> Unit) {
    JOutlinedTextFieldWithIconButton(
        text = contactPhoneNumber,
        onIconClick = onIconClick,
        icon = Icons.Default.Person,
        hint = R.string.str_feature_myjastic_contact_phone_number,
        readOnly = true
    )
}

@Composable
fun NavigationHandler(
    navState: Flow<JasticPointDetailNavState>,
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded
) {
    ObserveAsEvent(flow = navState, key1 = true) {
        when (it) {
            JasticPointDetailNavState.Idle -> Unit
            JasticPointDetailNavState.ToMyJasticList -> onRouteTo(Back)
            is JasticPointDetailNavState.ToDestinationSelectionMap -> {
                onPermissionNeeded(JasticPermission.Location) {
                    onRouteTo(
                        MapGraph(
                            latitude = it.latitude,
                            longitude = it.longitude,
                            radiusInMeters = it.radiusInMeters
                        )
                    )
                    //onRouteTo(MyJasticRoutes.Map, (this.toMapNavLocationData()))
                }
            }
        }
    }
}
