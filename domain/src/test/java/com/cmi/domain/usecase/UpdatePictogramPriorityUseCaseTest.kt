package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UpdatePictogramPriorityUseCaseTest{
    private lateinit var updatePictogramPriorityUseCase: UpdatePictogramPriorityUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        updatePictogramPriorityUseCase = UpdatePictogramPriorityUseCase(localDataSource)
    }

    @Test
    fun `execute update category use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))

        updatePictogramPriorityUseCase.invoke(pictogramAdded).single()

        val pictogramFromDisk = localDataSource.getPictograms().single().firstOrNull { it.pictogramId == pictogramAdded.pictogramId }

        val oldPriority = pictogramAdded.priority ?: 0
        val newPriority = pictogramFromDisk?.priority ?: 0
        assert(oldPriority + 1 == newPriority)
    }
}