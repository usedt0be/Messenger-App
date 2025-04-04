package com.example.messengerapp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PaginationColumn(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    loadIndex: Int = 5,
    loadItems: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: LazyListScope.() -> Unit
) {

    val shouldStartPaginate = remember {
        derivedStateOf {
            val currentLastVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo
                .lastOrNull()?.index ?: return@derivedStateOf true
            currentLastVisibleItemIndex >= lazyListState.layoutInfo.totalItemsCount - 1 - loadIndex
        }
    }

    LaunchedEffect(shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            loadItems()
        }
    }



    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = verticalArrangement,
        contentPadding= contentPadding,
        content = content
    )
}


@Composable
fun LazyColumnPager(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),

) {

    PaginationColumn(
        loadItems = {},
    ) { }
    LazyColumn() {

    }

}






