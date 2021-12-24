package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddCategoryUseCaseTest {

    private lateinit var addCategoryUseCase: AddCategoryUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        addCategoryUseCase = AddCategoryUseCase(localDataSource)
    }

    @Test
    fun `execute add category use case`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoryId = 130
        val categoryToAdd = FakeLocalDataSource.categoryFromDisk.copy(categoryId = categoryId)
        addCategoryUseCase.invoke(categoryToAdd).single()

        val categoryFromLocalDataSource =
            localDataSource.getCategories().single().firstOrNull { it.categoryId == categoryId }

        assert(categoryFromLocalDataSource == categoryToAdd)
    }
}
