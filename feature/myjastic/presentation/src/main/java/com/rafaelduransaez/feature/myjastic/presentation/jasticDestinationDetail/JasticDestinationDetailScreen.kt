package com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafaelduransaez.core.components.jButton.JSaveButton
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextField
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextFieldWithIconButton
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.permissions.JasticPermission
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.core.permissions.OnPermissionsNeeded
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailUserEvent.AliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailUserEvent.MessageUpdate
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes


@Composable
internal fun JasticDestinationDetailScreen(
    uiState: JasticDestinationDetailUiState,
    onUiEvent: (JasticDestinationDetailUserEvent) -> Unit,
    onRouteTo: NavRouteTo,
    contentPadding: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    onPermissionNeeded: OnPermissionNeeded
) {

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { contactUri: Uri? ->
            contactUri?.let {
                val contactIdentifier = contactUri.toString()
                onUiEvent(JasticDestinationDetailUserEvent.ContactSelected(contactIdentifier))
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
                                onRouteTo(MyJasticRoutes.Map(latitude, longitude))
                            }
                        }
                    }
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
            JSaveButton { onUiEvent(JasticDestinationDetailUserEvent.Save) }
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
fun Location(location: String, onIconClick: () -> Unit) {
    JOutlinedTextFieldWithIconButton(
        text = location,
        onIconClick = onIconClick,
        icon = Icons.Default.LocationOn,
        hint = R.string.str_feature_myjastic_location,
        placeHolder = R.string.str_feature_myjastic_location_placeholder,
        readOnly = true
    )
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