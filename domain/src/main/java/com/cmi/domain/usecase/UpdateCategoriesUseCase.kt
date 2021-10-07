package com.cmi.domain.usecase

import com.cmi.domain.entity.Category
import com.cmi.domain.system.CmiSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateCategoriesUseCase(private val cmiSystem: CmiSystem) {

    suspend operator fun invoke(categories: List<Category>) = flow {
        return@flow cmiSystem.updateCategories(categories = categories).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}