import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.repository.HomeRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import org.lotka.xenon.data.remote.pagination.GetItemByCategoryPagingSource
import org.lotka.xenon.domain.model.Items

class HomeRepositoryImpl @Inject constructor(
    private val realtimeDatabase: FirebaseDatabase
) : HomeRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
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

            emit(Resource.Success(categories))
            emit(Resource.Loading(false))

        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch categories: ${e.message}"))
        }
    }

    override fun getGetItem(): Flow<Resource<List<Items>>>  = flow {
        try {
            emit(Resource.Loading(true))

            val itemsList = mutableListOf<Items>()

            val ItemsReference = realtimeDatabase.getReference("Items")
            val snapshot = ItemsReference.get().await()

            snapshot.children.forEach { dataSnapshot ->
                val items = dataSnapshot.getValue(Items::class.java)
                if (items != null) {
                    itemsList.add(items)
                }
            }

            emit(Resource.Success(itemsList))
            emit(Resource.Loading(false))

        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch categories: ${e.message}"))
        }
    }

    override fun getDetailItem(itemId: String): Flow<Resource<Items>> = flow {
        try {
            emit(Resource.Loading(true))

            val itemReference = realtimeDatabase.getReference("Items/$itemId")
            val snapshot = itemReference.get().await()

            val item = snapshot.getValue(Items::class.java)
            if (item != null) {
                emit(Resource.Success(item))
            } else {
                emit(Resource.Error("Item not found"))
            }

            emit(Resource.Loading(false))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch item detail: ${e.message}"))
        }
    }

    override fun getItemsByCategory(categoryId: String): Flow<PagingData<Items>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false // Don't show placeholders
            ),
            pagingSourceFactory = { GetItemByCategoryPagingSource(realtimeDatabase, categoryId) }
        ).flow
    }


}