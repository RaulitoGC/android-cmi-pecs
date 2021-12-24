package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UpdatePictogramsUseCaseTest{
    private lateinit var updatePictogramUseCase: UpdatePictogramUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        updatePictogramUseCase = UpdatePictogramUseCase(localDataSource)
    }

    @Test
    fun `execute update category use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))
        val newName = "pictogram_new_name"
        val pictogramToUpdate = pictogramAdded.copy(name = newName)

        updatePictogramUseCase.invoke(pictogramToUpdate).single()

        val pictogramFromDisk = localDataSource.getPictograms().single().firstOrNull { it.pictogramId == pictogramAdded.pictogramId }

        assert(pictogramToUpdate == pictogramFromDisk)
    }
}