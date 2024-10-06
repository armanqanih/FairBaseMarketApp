package org.lotka.xenon.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Items
import org.lotka.xenon.domain.repository.HomeRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

class GetDetailItemUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(itemId: String): Flow<Resource<Items>> {
        return repository.getDetailItem(itemId)
    }
}
