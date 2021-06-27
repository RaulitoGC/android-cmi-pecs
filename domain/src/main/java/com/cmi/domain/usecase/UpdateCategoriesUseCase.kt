package com.cmi.domain.usecase

import com.cmi.domain.entity.Category
import com.cmi.domain.system.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateCategoriesUseCase(private val system: System) {

    suspend operator fun invoke(categories: List<Category>) = flow {
        return@flow system.updateCategories(categories = categories).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}