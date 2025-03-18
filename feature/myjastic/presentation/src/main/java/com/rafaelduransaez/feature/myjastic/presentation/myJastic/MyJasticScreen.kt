package com.rafaelduransaez.feature.myjastic.presentation.myJastic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.rafaelduransaez.core.components.common.ColumnItemsSpacer
import com.rafaelduransaez.core.components.common.JasticProgressIndicator
import com.rafaelduransaez.core.components.jButton.JButton
import com.rafaelduransaez.core.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jText.JDialogTextButton
import com.rafaelduransaez.core.components.jText.JTextCardBody
import com.rafaelduransaez.core.components.jText.JTextCardTitle
import com.rafaelduransaez.core.components.jText.JTextTitle
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.domain.extensions.negative
import com.rafaelduransaez.feature.myjastic.domain.model.JasticDestination
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes
import com.rafaelduransaez.feature.myjastic.presentation.utils.Constants.FIRST_ITEM_INDEX
import com.rafaelduransaez.feature.myjastic.presentation.utils.Constants.FIRST_ITEM_TO_SCROLL
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

            is MyJasticUiState.ShowMyJasticDestinations -> {
                if (uiState.jasticDestinations.isEmpty()) {
                    EmptyScreen()
                } else {
                    JasticDestinationsList(
                        jasticPoints = uiState.jasticDestinations,
                        paddingValues = contentPadding,
                        showScrollToTopButton = showScrollToTopButton,
                        listState = listState,
                        onJasticDestinationClicked = {
                            onRouteTo(
                                MyJasticRoutes.JasticDestinationDetail(
                                    it
                                )
                            )
                        }
                    )
                    AddFAB(
                        modifier = Modifier
                            .padding(JasticTheme.size.large)
                            .align(Alignment.BottomEnd)
                    ) {
                        onRouteTo(MyJasticRoutes.JasticDestinationDetail(Int.negative()))
                    }
                }
            }
        }
    }
}

@Composable
internal fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JIcon(
            icon = Icons.TwoTone.LocationOn,
            tint = JasticTheme.colorScheme.secondary,
            contentDescriptionResId = R.string.str_feature_myjastic_empty_state_title
        )
        ColumnItemsSpacer(JasticTheme.size.large)
        JTextTitle(textId = R.string.str_feature_myjastic_empty_state_title)
    }
}

@Composable
internal fun JasticDestinationsList(
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
            items(count = jasticPoints.size, key = { jasticPoints[it].alias }) { index ->
                JasticDestinationListItem(jasticPoints[index], onJasticDestinationClicked)
            }
        }

        AnimatedVisibility(showScrollToTopButton, modifier = Modifier.align(Alignment.TopCenter)) {
            ScrollToTopButton(coroutineScope, listState)
        }
    }
}

@Composable
internal fun BoxScope.ScrollToTopButton(
    coroutineScope: CoroutineScope,
    listState: LazyListState
) {
    JButton(
        modifier = Modifier.align(Alignment.TopCenter),
        textId = R.string.str_feature_myjastic_scroll_to_top
    ) {
        coroutineScope.launch {
            listState.animateScrollToItem(FIRST_ITEM_INDEX)
        }
    }
}


@Composable
internal fun JasticDestinationListItem(
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
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (headerRef, bodyRef, actionRef) = createRefs()

            JTextCardTitle(modifier = Modifier.constrainAs(headerRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = destination.alias)

            JTextCardBody(modifier = Modifier.constrainAs(bodyRef) {
                top.linkTo(headerRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = destination.alias)

            JButton(
                modifier = Modifier.constrainAs(actionRef) {
                    top.linkTo(headerRef.bottom)
                    bottom.linkTo(bodyRef.top)
                    end.linkTo(parent.end)
                },
                textId = R.string.str_feature_myjastic_go,
                containerColor = JasticTheme.colorScheme.tertiaryContainer
            ) { }
        }
    }
}
