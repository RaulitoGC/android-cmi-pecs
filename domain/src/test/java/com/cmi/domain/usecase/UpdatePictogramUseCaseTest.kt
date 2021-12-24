package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UpdatePictogramUseCaseTest{
    private lateinit var updatePictogramsUseCase: UpdatePictogramsUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        updatePictogramsUseCase = UpdatePictogramsUseCase(localDataSource)
    }

    @Test
    fun `execute update category use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource.pictogramsFromDisk
        val newName = "pictogram_new_name"
        val pictogramsToUpdate = pictogramAdded.map {
            it.copy(name = newName)
        }

        updatePictogramsUseCase.invoke(pictogramsToUpdate).single()

        val pictogramsFromDisk = localDataSource.getPictograms().single()

        assert(pictogramsToUpdate == pictogramsFromDisk)
    }
}