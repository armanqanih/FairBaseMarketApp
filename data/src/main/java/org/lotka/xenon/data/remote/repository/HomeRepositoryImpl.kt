import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.repository.HomeRepository


import kotlinx.coroutines.tasks.await
import org.lotka.xenon.data.remote.pagination.GetItemByCategoryPagingSource
import org.lotka.xenon.domain.model.Item


import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

import org.lotka.xenon.data.remote.dao.CategoryDao
import org.lotka.xenon.data.remote.dao.ItemsDao
import org.lotka.xenon.data.remote.dao.entity.toCategory

import org.lotka.xenon.data.remote.dao.entity.toCategoryEntity
import org.lotka.xenon.data.remote.dao.entity.toItems
import org.lotka.xenon.data.remote.dao.entity.toItemsEntity
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject


class HomeRepositoryImpl @Inject constructor(
    private val realtimeDatabase: FirebaseDatabase,
    private val categoryDao: CategoryDao,
    private val itemsDao: ItemsDao,
) : HomeRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        // First check if categories are available in Room
        val cachedCategories = categoryDao.getAllCategories().first()
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
                categoryDao.saveCategories(categories.map { it.toCategoryEntity() })

                emit(Resource.Success(categories))
                emit(Resource.Loading(false))

            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch categories: ${e.message}"))
            }
        }
    }

    override fun getHomeItem(): Flow<Resource<List<Item>>> = flow {
        val cachedItems = itemsDao.getAllItems().first()
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
                itemsDao.saveHomeItems(itemList.map { it.toItemsEntity() })

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
            val cachedItem = itemsDao.getItemById(itemId)
            if (cachedItem != null) {
                emit(Resource.Success(cachedItem.toItems()))
            } else {
                // If item is not found in Room, fetch from Firebase
                val itemReference = realtimeDatabase.getReference("Items/$itemId")
                val snapshot = itemReference.get().await()

                val item = snapshot.getValue(Item::class.java)
                if (item != null) {
                    itemsDao.saveDetailItem(item.toItemsEntity())
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

    override suspend fun saveItemToCart(item: Item) {
        itemsDao.saveItemToCart(item.toItemsEntity())
    }

    override fun getItemsToCard(): Flow<List<Item>> {
        return itemsDao.getItemToCard().map { itemsEntityList ->
            itemsEntityList.map { entity ->
                entity.toItems() // Convert each ItemsEntity to Items
            }
        }
    }

    override suspend fun removeItemFromCart(itemId: String) {
        itemsDao.removeItemFromCart(itemId)
    }

}
