package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLastPecsPictogramsUseCaseTest{

    private lateinit var getLastPecsPictogramsUseCase: GetLastPecsPictogramsUseCase
    private lateinit var localDataSource: LocalDataSource

    companion object {
        const val MAIN_PICTOGRAM_ID_TO_BE_ADDED = 201
        const val FIRST_ACTION_ID_TO_BE_ADDED = 202
        const val SECOND_ACTION_ID_TO_BE_ADDED = 203
        const val ATTRIBUTE_ID_TO_BE_ADDED = 204
        const val SECOND_ATTRIBUTE_ID_TO_BE_ADDED = 205
    }

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        getLastPecsPictogramsUseCase = GetLastPecsPictogramsUseCase(localDataSource)
    }

    @Test
    fun `execute get last pictograms with main pictogram id added use case`() = runBlocking {
        FakeLocalDataSource.reset()

        localDataSource.setMainPictogramId(MAIN_PICTOGRAM_ID_TO_BE_ADDED).single()
        val map = getLastPecsPictogramsUseCase.invoke().single()

        assert(map.containsKey(SavePictogramPecsIdUseCase.PICTOGRAM_MAIN))
        assert(map[SavePictogramPecsIdUseCase.PICTOGRAM_MAIN]?.pictogramId == MAIN_PICTOGRAM_ID_TO_BE_ADDED)
    }

    @Test
    fun `execute get last pictograms with first action pictogram id added use case`() = runBlocking {
        FakeLocalDataSource.reset()

        localDataSource.setFirstActionPictogramId(FIRST_ACTION_ID_TO_BE_ADDED).single()
        val map = getLastPecsPictogramsUseCase.invoke().single()

        assert(map.containsKey(SavePictogramPecsIdUseCase.PICTOGRAM_FIRST_ACTION))
        assert(map[SavePictogramPecsIdUseCase.PICTOGRAM_FIRST_ACTION]?.pictogramId == FIRST_ACTION_ID_TO_BE_ADDED)
    }

    @Test
    fun `execute get last pictograms with second action pictogram id added use case`() = runBlocking {
        FakeLocalDataSource.reset()

        localDataSource.setSecondActionPictogramId(SECOND_ACTION_ID_TO_BE_ADDED).single()
        val map = getLastPecsPictogramsUseCase.invoke().single()

        assert(map.containsKey(SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ACTION))
        assert(map[SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ACTION]?.pictogramId == SECOND_ACTION_ID_TO_BE_ADDED)
    }

    @Test
    fun `execute get last pictograms with attribute pictogram id added use case`() = runBlocking {
        FakeLocalDataSource.reset()

        localDataSource.setAttributePictogramId(ATTRIBUTE_ID_TO_BE_ADDED).single()
        val map = getLastPecsPictogramsUseCase.invoke().single()

        assert(map.containsKey(SavePictogramPecsIdUseCase.PICTOGRAM_ATTRIBUTE))
        assert(map[SavePictogramPecsIdUseCase.PICTOGRAM_ATTRIBUTE]?.pictogramId == ATTRIBUTE_ID_TO_BE_ADDED)
    }

    @Test
    fun `execute get last pictograms with second attribute pictogram id added use case`() = runBlocking {
        FakeLocalDataSource.reset()

        localDataSource.setSecondAttributePictogramId(SECOND_ATTRIBUTE_ID_TO_BE_ADDED).single()
        val map = getLastPecsPictogramsUseCase.invoke().single()

        assert(map.containsKey(SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE))
        assert(map[SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE]?.pictogramId == SECOND_ATTRIBUTE_ID_TO_BE_ADDED)
    }
}