package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import com.cmi.domain.util.FakeLocalDataSource.Companion.INVALID_ID
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class SavePictogramPecsIdUseCaseTest {

    private lateinit var savePictogramPecsIdUseCase: SavePictogramPecsIdUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        savePictogramPecsIdUseCase = SavePictogramPecsIdUseCase(localDataSource)
    }

    @Test
    fun `execute save main pictogram pecs id use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))

        savePictogramPecsIdUseCase
            .invoke(
                type = SavePictogramPecsIdUseCase.PICTOGRAM_MAIN,
                pictogramId = pictogramAdded.pictogramId ?: INVALID_ID
            )
            .single()

        val mainPictogramIdFromDisk = localDataSource.getMainPictogramId()

        assert(pictogramAdded.pictogramId == mainPictogramIdFromDisk)
    }

    @Test
    fun `execute save first action pictogram pecs id use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))

        savePictogramPecsIdUseCase
            .invoke(
                type = SavePictogramPecsIdUseCase.PICTOGRAM_FIRST_ACTION,
                pictogramId = pictogramAdded.pictogramId ?: INVALID_ID
            )
            .single()

        val firstActionPictogramIdFromDisk = localDataSource.getFirstActionPictogramId()

        assert(pictogramAdded.pictogramId == firstActionPictogramIdFromDisk)
    }

    @Test
    fun `execute save second action pictogram pecs id use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))

        savePictogramPecsIdUseCase
            .invoke(
                type = SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ACTION,
                pictogramId = pictogramAdded.pictogramId ?: INVALID_ID
            )
            .single()

        val secondActionPictogramIdFromDisk = localDataSource.getSecondActionPictogramId()

        assert(pictogramAdded.pictogramId == secondActionPictogramIdFromDisk)
    }

    @Test
    fun `execute save attribute pictogram pecs id use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))

        savePictogramPecsIdUseCase
            .invoke(
                type = SavePictogramPecsIdUseCase.PICTOGRAM_ATTRIBUTE,
                pictogramId = pictogramAdded.pictogramId ?: INVALID_ID
            )
            .single()

        val attributePictogramIdFromDisk = localDataSource.getAttributePictogramId()

        assert(pictogramAdded.pictogramId == attributePictogramIdFromDisk)
    }

    @Test
    fun `execute save second attribute pictogram pecs id use case`() = runBlocking {
        FakeLocalDataSource.reset()

        val pictogramAdded = FakeLocalDataSource
            .pictogramsFromDisk
            .get(Random.nextInt(0, FakeLocalDataSource.SIZE_FOR_LIST))

        savePictogramPecsIdUseCase
            .invoke(
                type = SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE,
                pictogramId = pictogramAdded.pictogramId ?: INVALID_ID
            )
            .single()

        val secondAttributePictogramIdFromDisk = localDataSource.getSecondPictogramAttributeId()

        assert(pictogramAdded.pictogramId == secondAttributePictogramIdFromDisk)
    }
}