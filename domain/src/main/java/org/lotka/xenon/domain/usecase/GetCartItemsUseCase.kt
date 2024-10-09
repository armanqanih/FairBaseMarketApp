package org.lotka.xenon.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
      operator fun invoke(): Flow<List<Item>> {
        return itemsRepository.getItemsToCard()
    }
}