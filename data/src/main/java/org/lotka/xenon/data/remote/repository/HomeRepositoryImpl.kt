import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.repository.HomeRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
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

    override suspend fun getItemsByCategory(categoryId: Int): Flow<Resource<List<Items>>>  = flow {
        try {
            emit(Resource.Loading(true))

            val itemsReference = realtimeDatabase.getReference("Items")
            val snapshot = itemsReference
                .orderByChild("categoryId")
                .equalTo(categoryId.toDouble())  // Ensure categoryId in Firebase is stored as a Double
                .get().await()

            val itemsList = snapshot.children.mapNotNull { it.getValue(Items::class.java) }

            emit(Resource.Success(itemsList))
            emit(Resource.Loading(false))

        } catch (e: Exception) {
            Log.e("HomeRepository", "Error fetching items by category", e)
            emit(Resource.Error("Failed to fetch items by category: ${e.message}"))
        }
    }
}