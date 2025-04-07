package com.example.messengerapp.core.ui

import android.util.Log
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
    preloadIndex: Int = 5,
    loadItems: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: LazyListScope.() -> Unit
) {

    val shouldStartPaginate = remember {
        derivedStateOf {
            val currentLastVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo
                .lastOrNull()?.index ?: return@derivedStateOf true
            Log.d("chat_COLUMN_last_index", "$currentLastVisibleItemIndex")
            currentLastVisibleItemIndex >= lazyListState.layoutInfo.totalItemsCount - 1 - preloadIndex
        }
    }
    Log.d("chat_COLUMN_PAGING_STATE", "$shouldStartPaginate")

    LaunchedEffect(shouldStartPaginate.value) {
        Log.d("chat_COLUMN_PAGING_EFFECT", "triggered")
        if (shouldStartPaginate.value) {
            Log.d("chat_COLUMN_PAGING_EFFECT", "triggered_true")
            loadItems()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding= contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        content = content
    )
}









