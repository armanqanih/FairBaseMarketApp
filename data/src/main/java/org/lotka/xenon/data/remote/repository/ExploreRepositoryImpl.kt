import android.os.Build
import androidx.annotation.RequiresApi

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.repository.ExploreRepository


import kotlinx.coroutines.tasks.await
import org.lotka.xenon.data.remote.pagination.GetItemByCategoryPagingSource
import org.lotka.xenon.domain.model.Item


import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

import org.lotka.xenon.data.remote.dao.local.CategoryDao
import org.lotka.xenon.data.remote.dao.local.ItemsDao
import org.lotka.xenon.data.remote.dao.local.database.ItemDatabase

import org.lotka.xenon.data.remote.dao.local.entity.toCategory


import org.lotka.xenon.data.remote.dao.local.entity.toCategoryEntity
import org.lotka.xenon.data.remote.dao.local.entity.toItems
import org.lotka.xenon.data.remote.dao.local.entity.toItemsEntity
import org.lotka.xenon.data.remote.dao.local.entity.toWishListEntity
import org.lotka.xenon.data.remote.dao.local.entity.toWishListModel
import org.lotka.xenon.data.util.IsNetworkAvailable

import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject


class ExploreRepositoryImpl @Inject constructor(
    private val realtimeDatabase: FirebaseDatabase,
    private val db: ItemDatabase,
    private val context: android.content.Context
    ) : ExploreRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        // First check if categories are available in Room
        val cachedCategories = db.categoryDao().getAllCategories().first()
        if (cachedCategories.isNotEmpty()) {
            emit(Resource.Success(cachedCategories.map { it.toCategory() }))
        } else {
            // Fetch from Firebase if Room is empty
            try {
                emit(Resource.Loading(true))
                val categories = mutableListOf<Category>()

                val categoriesReference = realtimeDatabase.getReference("Category")
                val snapshot = categoriesReference.get().await()

                snapshot.children.forEach { dataSnapshot ->
                    val category = dataSnapshot.getValue(Category::class.java)
                    if (category != null) {
                        categories.add(category)
                    }
                }


                // Save to Room after fetching
                db.categoryDao().saveCategories(categories.map { it.toCategoryEntity() })

                emit(Resource.Loading(false))
                emit(Resource.Success(categories))

            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch categories: ${e.message}"))
            }
        }
    }

    override fun getHomeItem(): Flow<Resource<List<Item>>> = flow {
        val cachedItems = db
            .itemsDao().getAllItems().first()
        if (cachedItems.isNotEmpty()) {
            emit(Resource.Success(cachedItems.map { it.toItems() }))
        } else {
            try {
                emit(Resource.Loading(true))
                val itemList = mutableListOf<Item>()

                val itemsReference = realtimeDatabase.getReference("Items")
                val snapshot = itemsReference.get().await()

                snapshot.children.forEach { dataSnapshot ->
                    val item = dataSnapshot.getValue(Item::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }

                // Save fetched items to Room
                db.itemsDao().saveHomeItems(itemList.map { it.toItemsEntity() })

                emit(Resource.Success(itemList))
                emit(Resource.Loading(false))

            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch items: ${e.message}"))
            }
        }
    }

    override fun getDetailItem(itemId: String): Flow<Resource<Item>> = flow {
        // Emit loading state
        emit(Resource.Loading(true))

        try {
            // Check if the item exists in Room first
            val cachedItem = db.itemsDao().getItemById(itemId)
            if (cachedItem != null) {
                emit(Resource.Success(cachedItem.toItems()))
            } else {
                // If item is not found in Room, fetch from Firebase
                val itemReference = realtimeDatabase.getReference("Items/$itemId")
                val snapshot = itemReference.get().await()

                val item = snapshot.getValue(Item::class.java)
                if (item != null) {
                    db.itemsDao().saveDetailItem(item.toItemsEntity())
                    emit(Resource.Success(item))
                } else {
                    emit(Resource.Error("Item not found in Firebase"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch item detail: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }


    override fun getItemsByCategory(categoryId: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { GetItemByCategoryPagingSource(realtimeDatabase, categoryId) }
        ).flow
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun searchItems(query: String): Flow<Resource<List<Item>>> = flow {
        try {
            emit(Resource.Loading(true))

            val isConnected = IsNetworkAvailable(context = context)
            if (isConnected) {
                // Fetch data from Firebase
                val searchResults = mutableListOf<Item>()
                val itemsReference = realtimeDatabase.getReference("Items")
                val snapshot = itemsReference.get().await()

                snapshot.children.forEach { dataSnapshot ->
                    val item = dataSnapshot.getValue(Item::class.java)
                    if (item != null && item.title?.contains(query, ignoreCase = true) == true) {
                        searchResults.add(item)
                    }
                }

                if (searchResults.isNotEmpty()) {
                    // Save results to Room and emit success
                    db.itemsDao().saveHomeItems(searchResults.map { it.toItemsEntity() })
                    emit(Resource.Success(searchResults))
                } else {
                    emit(Resource.Error("No items found"))
                }
            } else {
                // Offline search in Room
                val cachedItems = db.itemsDao().searchItemsInRoom("%$query%").first()
                if (cachedItems.isNotEmpty()) {
                    emit(Resource.Success(cachedItems.map { it.toItems() }))
                } else {
                    emit(Resource.Error("No items found locally"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to search items: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }



}

