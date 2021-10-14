package com.cmi.domain.di

import com.cmi.domain.system.CmiSystem
import com.cmi.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideAddCategoryUseCase(cmiSystem: CmiSystem) = AddCategoryUseCase(cmiSystem)

    @Provides
    fun provideAddPictogramUseCase(cmiSystem: CmiSystem) = AddPictogramUseCase(cmiSystem)

    @Provides
    fun provideCleanLastPictogramUseCase(cmiSystem: CmiSystem) = CleanLastPictogramsUseCase(cmiSystem)

    @Provides
    fun provideDeleteCategoriesUseCase(cmiSystem: CmiSystem) = DeleteCategoriesUseCase(cmiSystem)

    @Provides
    fun provideDeletePictogramsUseCase(cmiSystem: CmiSystem) =  DeletePictogramsUseCase(cmiSystem)

    @Provides
    fun providesGetCategoriesUseCase(cmiSystem: CmiSystem) = GetCategoriesUseCase(cmiSystem)

    @Provides
    fun provideGetLastPecsPictogramsUseCase(cmiSystem: CmiSystem) = GetLastPecsPictogramsUseCase(cmiSystem)

    @Provides
    fun provideSavePictogramsPecsUseCase(cmiSystem: CmiSystem) = SavePictogramPecsIdUseCase(cmiSystem)

    @Provides
    fun provideUpdateCategoriesUseCase(cmiSystem: CmiSystem) = UpdateCategoriesUseCase(cmiSystem)

    @Provides
    fun provideGetPictogramsByCategoryUseCase( cmiSystem: CmiSystem) = GetPictogramsByCategoryUseCase(cmiSystem)

    @Provides
    fun provideUpdateCategoryUseCase(cmiSystem: CmiSystem) = UpdateCategoryUseCase(cmiSystem)

    @Provides
    fun provideUpdatePictogramPriorityUseCase(cmiSystem: CmiSystem) = UpdatePictogramPriorityUseCase(cmiSystem)

    @Provides
    fun provideUpdatePictogramUseCase(cmiSystem: CmiSystem) = UpdatePictogramUseCase(cmiSystem)

    @Provides
    fun provideUpdatePictogramsUseCase(cmiSystem: CmiSystem) = UpdatePictogramsUseCase(cmiSystem)
}