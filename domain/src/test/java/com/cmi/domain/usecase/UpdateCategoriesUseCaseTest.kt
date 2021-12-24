package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UpdateCategoriesUseCaseTest{
    private lateinit var updateCategoriesUseCase: UpdateCategoriesUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        updateCategoriesUseCase = UpdateCategoriesUseCase(localDataSource)
    }

    @Test
    fun `execute update categories use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val categoriesAdded = FakeLocalDataSource.categoriesFromDisk
        val newName = "new_name+"
        val categoriesToUpdate = categoriesAdded.mapIndexed { index, category ->
            category.copy(name = "$newName$index")
        }

        updateCategoriesUseCase.invoke(categoriesToUpdate).single()

        val categoriesFromDisk = localDataSource.getCategories().single()

        assert(categoriesToUpdate == categoriesFromDisk)
    }
}