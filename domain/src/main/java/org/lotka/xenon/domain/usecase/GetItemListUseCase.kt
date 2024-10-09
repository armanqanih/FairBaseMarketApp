package org.lotka.xenon.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.HomeRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<Resource<List<Item>>> {
        return homeRepository.getHomeItem()
    }
}


