package org.lotka.xenon.domain.usecase.explorer

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.ExploreRepository
import javax.inject.Inject

class GetItemByCategoryUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
     operator fun invoke(category:String): Flow<PagingData<Item>> {
        return exploreRepository.getItemsByCategory(category)
    }
}


