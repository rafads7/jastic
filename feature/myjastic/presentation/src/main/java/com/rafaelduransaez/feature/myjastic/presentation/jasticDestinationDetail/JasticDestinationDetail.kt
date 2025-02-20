package com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelduransaez.core.components.jButton.JSaveButton
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextField
import com.rafaelduransaez.core.components.jTextField.JOutlinedTextFieldWithIconButton
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailUserEvent.AliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailUserEvent.LocationUpdate
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes

@Composable
internal fun JasticDestinationDetailScreen(
    uiState: JasticDestinationDetailUiState,
    onUiEvent: (JasticDestinationDetailUserEvent) -> Unit,
    onRouteTo: NavRouteTo,
    contentPadding: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
) {
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
                    location = uiState.location,
                    onLocationChanges = { onUiEvent(LocationUpdate(it)) },
                    onIconClick = { onRouteTo(MyJasticRoutes.Map) }
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
fun Location(location: String, onLocationChanges: (String) -> Unit, onIconClick: () -> Unit) {
    JOutlinedTextFieldWithIconButton(
        text = location,
        onIconClick = onIconClick,
        icon = Icons.Default.LocationOn,
        hint = R.string.str_feature_myjastic_location,
        onValueChange = onLocationChanges,
        readOnly = true
    )
}