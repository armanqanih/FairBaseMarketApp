package org.lotka.xenon.domain.usecase.explorer

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.ExploreRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

class SearchItemUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Item>>> {
        return exploreRepository.searchItems(query)
    }
}


