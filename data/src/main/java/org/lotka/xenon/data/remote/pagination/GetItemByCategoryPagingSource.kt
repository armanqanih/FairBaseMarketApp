package org.lotka.xenon.data.remote.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.FirebaseDatabase



import kotlinx.coroutines.tasks.await
import org.lotka.xenon.domain.model.Items

class GetItemByCategoryPagingSource(
    private val realtimeDatabase: FirebaseDatabase,
    private val categoryId: String
) : PagingSource<Int, Items>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        return try {
            // Determine the current page (use 1 as the default)
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize // Page size requested by Paging library

            // Reference to the Firebase Realtime Database
            val itemsReference = realtimeDatabase.getReference("Items")

            // Query the database based on categoryId with pagination logic
            val snapshot = itemsReference
                .orderByChild("categoryId")
                .equalTo(categoryId)  // Filter by categoryId
                .limitToFirst(pageSize * currentPage) // Fetch items for the current page
                .get().await() // Await the result to get snapshot

            // Convert the snapshot into a list of Items
            val itemsList = snapshot.children.mapNotNull { it.getValue(Items::class.java) }

            // Check if more pages are available
            val nextKey = if (itemsList.size < pageSize) null else currentPage + 1
            val prevKey = if (currentPage == 1) null else currentPage - 1

            // Return the loaded page of data
            LoadResult.Page(
                data = itemsList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            // Handle errors here (e.g., network issues, Firebase failures)
            LoadResult.Error(e)
        }
    }

    // This method tells Paging how to get a refresh key for the next page
    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
