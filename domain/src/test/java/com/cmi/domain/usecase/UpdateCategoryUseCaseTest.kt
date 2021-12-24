package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UpdateCategoryUseCaseTest{
    private lateinit var updateCategoryUseCase: UpdateCategoryUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        updateCategoryUseCase = UpdateCategoryUseCase(localDataSource)
    }

    @Test
    fun `execute update category use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val newCategoryName = "new_category_name"
        val categoryAdded = FakeLocalDataSource
            .categoriesFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))
        val categoryToUpdate = categoryAdded.copy(name = newCategoryName)

        updateCategoryUseCase.invoke(categoryToUpdate).single()

        val categoriesFromDisk = localDataSource.getCategories().single().firstOrNull { it.categoryId == categoryToUpdate.categoryId }

        assert(categoryToUpdate == categoriesFromDisk)
    }
}