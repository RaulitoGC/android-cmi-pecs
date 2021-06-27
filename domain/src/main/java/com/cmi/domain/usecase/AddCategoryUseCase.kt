package com.cmi.domain.usecase

import com.cmi.domain.entity.Category
import com.cmi.domain.system.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddCategoryUseCase(private val system: System) {

    suspend operator fun invoke(category: Category) = flow {
        return@flow system.addCategory(category = category).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}