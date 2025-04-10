package com.rafaelduransaez.feature.myjastic.presentation.myJastic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.twotone.Build
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rafaelduransaez.core.components.common.ColumnItemsSpacer
import com.rafaelduransaez.core.components.common.JasticDatabaseError
import com.rafaelduransaez.core.components.common.JasticEmptyScreen
import com.rafaelduransaez.core.components.common.JasticProgressIndicator
import com.rafaelduransaez.core.components.common.JasticScrollToTopButton
import com.rafaelduransaez.core.components.jButton.JButton
import com.rafaelduransaez.core.components.jCard.JCard
import com.rafaelduransaez.core.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jCard.JCardBody
import com.rafaelduransaez.core.components.jCard.JCardHeader
import com.rafaelduransaez.core.components.jCard.JCardIconAction
import com.rafaelduransaez.core.components.jText.JTextTitle
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.domain.extensions.negative
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.Constants.FIRST_ITEM_INDEX
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.Constants.FIRST_ITEM_TO_SCROLL
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun MyJasticScreen(
    uiState: MyJasticUiState,
    contentPadding: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    onRouteTo: NavRouteTo

) {
    val listState = rememberLazyListState()
    val showScrollToTopButton by remember { derivedStateOf { listState.firstVisibleItemIndex > FIRST_ITEM_TO_SCROLL } }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState) {
            is MyJasticUiState.Loading -> {
                JasticProgressIndicator()
            }

            is MyJasticUiState.ShowMyJasticPoints -> {
                if (uiState.jasticPoints.isEmpty()) {
                    JasticEmptyScreen(textId = R.string.str_feature_myjastic_empty_state_title)
                } else {
                    JasticPointsList(
                        jasticPoints = uiState.jasticPoints,
                        paddingValues = contentPadding,
                        showScrollToTopButton = showScrollToTopButton,
                        listState = listState,
                        onJasticPointClicked = { onRouteTo(MyJasticRoutes.JasticPointDetail(it)) }
                    )
                }
            }

            MyJasticUiState.Error -> {
                JasticDatabaseError(textId = R.string.str_feature_myjastic_error_state_title)
            }
        }
        AddFAB(
            modifier = Modifier
                .padding(JasticTheme.size.large)
                .align(Alignment.BottomEnd)
        ) {
            onRouteTo(MyJasticRoutes.JasticPointDetail())
        }
    }
}

@Composable
internal fun JasticPointsList(
    jasticPoints: List<JasticPointListItemUI>,
    paddingValues: PaddingValues = PaddingValues(all = JasticTheme.size.normal),
    showScrollToTopButton: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onJasticPointClicked: (Long) -> Unit
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(JasticTheme.size.minimum),
            state = listState
        ) {
            items(count = jasticPoints.size, key = { jasticPoints[it].alias }) { index ->
                JasticPointListItem(jasticPoints[index], onJasticPointClicked)
            }
        }

        AnimatedVisibility(showScrollToTopButton, modifier = Modifier.align(Alignment.TopCenter)) {
            JasticScrollToTopButton(coroutineScope = coroutineScope, listState = listState)
        }
    }
}


@Composable
internal fun JasticPointListItem(
    point: JasticPointListItemUI,
    onJasticPointClicked: (id: Long) -> Unit
) {
    JCard(
        onClick = { onJasticPointClicked(point.id) }
    ) {
        Box(Modifier.fillMaxSize()) {
            Column {
                JCardHeader(text = point.alias)
                JCardBody(text = point.alias)
                JCardBody(text = point.alias)
                JCardBody(text = point.alias)
            }
            JCardIconAction(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Default.Edit
            ) {
                onJasticPointClicked(point.id)
            }
        }
    }
}

object Constants {
    const val FIRST_ITEM_INDEX = 0
    const val FIRST_ITEM_TO_SCROLL = 2
}
