package org.lotka.xenon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items
import org.lotka.xenon.domain.util.Resource

interface HomeRepository {

    fun getCategories(): Flow<Resource<List<Category>>>
    fun getGetItem(): Flow<Resource<List<Items>>>

}