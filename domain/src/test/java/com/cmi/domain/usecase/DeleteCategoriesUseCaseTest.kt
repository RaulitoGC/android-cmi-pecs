package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteCategoriesUseCaseTest {

    private lateinit var deleteCategoriesUseCase: DeleteCategoriesUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        deleteCategoriesUseCase = DeleteCategoriesUseCase(localDataSource)
    }

    @Test
    fun `execute delete categories use case`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoriesAdded = FakeLocalDataSource.categoriesFromDisk

        deleteCategoriesUseCase.invoke(categoriesAdded).single()

        val categoriesFromDisk = localDataSource.getCategories().single()
        assert(categoriesFromDisk.isEmpty())
    }
}