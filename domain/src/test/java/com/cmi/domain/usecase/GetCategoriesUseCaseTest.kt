package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCategoriesUseCaseTest{

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        getCategoriesUseCase = GetCategoriesUseCase(localDataSource)
    }

    @Test
    fun `execute get categories use case`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoriesAdded = FakeLocalDataSource.categoriesFromDisk

        val categoriesFromDisk = getCategoriesUseCase.invoke().single()

        assert(categoriesFromDisk == categoriesAdded)
    }
}
