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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rafaelduransaez.core.components.jButton.JSaveAndGoButton
import com.rafaelduransaez.core.components.jButton.JSaveButton
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextField
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextFieldWithIconButton
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.ui.permissions.JasticPermission
import com.rafaelduransaez.core.ui.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.AliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.MessageUpdate

@Composable
internal fun JasticPointDetailScreen(
    uiState: JasticPointDetailUiState,
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Alias(
                    alias = uiState.alias,
                    onAliasChanges = { onUiEvent(AliasUpdate(it)) }
                )
                Location(
                    location = uiState.location.address,
                    onIconClick = {
                        onPermissionNeeded(JasticPermission.Location) {
                            with(uiState.location) {
                                onRouteTo(NavigationGraphs.MapGraph(latitude, longitude, radiusInMeters))
                                //onRouteTo(MyJasticRoutes.Map, (this.toMapNavLocationData()))
                            }
                        }
                    },
                    onSaveLocationCheck = { onUiEvent(JasticPointDetailUserEvent.SaveLocation(it)) }
                )
                ContactPhoneNumber(
                    contactPhoneNumber = uiState.contact.phoneNumber,
                    onIconClick = {
                        onPermissionNeeded(JasticPermission.Contacts) {
                            contactPickerLauncher.launch(null)
                        }
                    }
                )
                Message(
                    message = uiState.message,
                    onMessageChanges = { onUiEvent(MessageUpdate(it)) }
                )
            }
            JSaveButton { onUiEvent(JasticPointDetailUserEvent.Save) }
            JSaveAndGoButton { onUiEvent(JasticPointDetailUserEvent.SaveAndGo) }
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
fun Location(location: String, onIconClick: () -> Unit, onSaveLocationCheck: (Boolean) -> Unit) {
    var isChecked by rememberSaveable { mutableStateOf(true) }

    JOutlinedTextFieldWithIconButton(
        text = location,
        onIconClick = onIconClick,
        icon = Icons.Default.LocationOn,
        hint = R.string.str_feature_myjastic_location,
        placeHolder = R.string.str_feature_myjastic_location_placeholder,
        readOnly = true
    )

    Row(
        modifier = Modifier.clickable {
            isChecked = !isChecked
            onSaveLocationCheck(isChecked)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = JasticTheme.colorScheme.secondaryContainer,
                uncheckedColor = JasticTheme.colorScheme.secondary,
                checkmarkColor = JasticTheme.colorScheme.secondary
            )
        )
        JText(textId = R.string.str_feature_myjastic_save_location_future)
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
        hint = R.string.str_feature_myjastic_contact_phone_number
    )
}