package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddPictogramUseCaseTest {
    private lateinit var addPictogramUseCase: AddPictogramUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        addPictogramUseCase = AddPictogramUseCase(localDataSource)
    }

    @Test
    fun `execute add pictogram use case`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictogramId = 140
        val pictogramToAdd = FakeLocalDataSource.pictogramFromDisk.copy(pictogramId = pictogramId)
        addPictogramUseCase.invoke(pictogramToAdd).single()

        val pictogramFromLocalDataSource =
            localDataSource.getPictograms().single().firstOrNull { it.pictogramId == pictogramId }

        assert(pictogramFromLocalDataSource == pictogramToAdd)
    }
}