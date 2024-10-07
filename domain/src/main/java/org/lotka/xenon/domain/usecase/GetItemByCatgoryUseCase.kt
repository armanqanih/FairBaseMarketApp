package org.lotka.xenon.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items
import org.lotka.xenon.domain.repository.HomeRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

class GetItemByCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
     operator fun invoke(category:String): Flow<PagingData<Items>> {
        return homeRepository.getItemsByCategory(category)
    }
}


