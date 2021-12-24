package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetPictogramsByCategoryUseCaseTest{

    private lateinit var getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(localDataSource)
    }

    @Test
    fun `execute get pictograms for category use case`() = runBlocking {
        FakeLocalDataSource.reset()

        localDataSource.deletePictograms(FakeLocalDataSource.pictogramsFromDisk).single()
        val categoryId = 60
        val pictograms = FakeLocalDataSource.pictogramsFromDisk.mapIndexed { index, pictogram ->
            pictogram.copy(pictogramId = index + categoryId, categoryId = categoryId)
        }
        pictograms.forEach {
            localDataSource.addPictogram(it).single()
        }

        val pictogramsFromDisk = getPictogramsByCategoryUseCase.invoke(categoryId = categoryId).single()

        assert(pictograms == pictogramsFromDisk)
    }
}