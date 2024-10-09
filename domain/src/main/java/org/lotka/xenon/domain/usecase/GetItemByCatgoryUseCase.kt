package org.lotka.xenon.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class GetItemByCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
     operator fun invoke(category:String): Flow<PagingData<Item>> {
        return homeRepository.getItemsByCategory(category)
    }
}


