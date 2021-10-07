package com.cmi.domain.usecase

import com.cmi.domain.system.CmiSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategoriesUseCase(private val cmiSystem: CmiSystem) {

    suspend operator fun invoke() = flow {
        return@flow cmiSystem.getCategories().collect { categories ->
            emit(categories)
        }
    }.flowOn(Dispatchers.IO)

}