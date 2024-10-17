package com.cmi.presentation.di

import com.cmi.data.di.DataServiceLocator
import com.cmi.data.local.preferences.SurveyPreferences
import com.cmi.domain.usecase.*
import com.cmi.presentation.components.category.CategorySelectableViewModel
import com.cmi.presentation.config.add.PictureLoaderViewModel
import com.cmi.presentation.config.add.category.AddCategoryViewModel
import com.cmi.presentation.config.add.pictogram.AddPictogramViewModel
import com.cmi.presentation.config.category.select.SelectCategoriesForPecsViewModel
import com.cmi.presentation.config.delete.category.DeleteCategoryViewModel
import com.cmi.presentation.config.delete.pictogram.DeletePictogramViewModel
import com.cmi.presentation.config.edit.SelectCategoryViewModel
import com.cmi.presentation.config.edit.category.EditCategoryViewModel
import com.cmi.presentation.config.edit.pictogram.EditPictogramViewModel
import com.cmi.presentation.config.edit.pictogram.select.SelectPictogramViewModel
import com.cmi.presentation.config.select.category.SelectCategoryForPecsViewModel
import com.cmi.presentation.config.select.pictogram.SelectPictogramForPecsViewModel
import com.cmi.presentation.intro.SurveyValidator
import com.cmi.presentation.manager.DefaultStringResourceManager
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.pecs.category.CategoryViewModel
import com.cmi.presentation.pecs.pictogram.PictogramViewModel
import com.cmi.presentation.pecs.tape.TapeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { (itemsPerScreen: Int) ->
        CategoryViewModel(
            itemsPerScreen = itemsPerScreen,
            cleanLastPictogramsUseCase = CleanLastPictogramsUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (itemsPerScreen: Int) ->
        AddPictogramViewModel(
            itemsPerScreen = itemsPerScreen,
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            addPictogramUseCase = AddPictogramUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        AddCategoryViewModel(
            addCategoryUseCase = AddCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        SelectCategoryViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        EditCategoryViewModel(
            updateCategoryUseCase = UpdateCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        SelectPictogramViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        EditPictogramViewModel(
            updatePictogramUseCase = UpdatePictogramUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        SelectCategoryForPecsViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updateCategoriesUseCase = UpdateCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        SelectPictogramForPecsViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updatePictogramsUseCase = UpdatePictogramsUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        DeleteCategoryViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            deleteCategoriesUseCase = DeleteCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        DeletePictogramViewModel(
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            deletePictogramsUseCase = DeletePictogramsUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        PictogramViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updatePictogramPriorityUseCase = UpdatePictogramPriorityUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            savePictogramPecsIdUseCase = SavePictogramPecsIdUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            getLastPecsPictogramsUseCase = GetLastPecsPictogramsUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        TapeViewModel(
            getLastPecsPictogramsUseCase = GetLastPecsPictogramsUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    single {
        val context = androidContext()
        SurveyValidator(
            context = context,
            surveyPreferences = SurveyPreferences(
                DataServiceLocator.provideSharedPreferences(
                    context = context,
                    name = "cmi_pecs_survey_preferences"
                )
            )
        )
    }

    single<StringResourceManager> {
        DefaultStringResourceManager(
            context = androidContext()
        )
    }

    // Category Selectable
    viewModel {
        CategorySelectableViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    // Picture Loader
    viewModel { parameters ->
        PictureLoaderViewModel(
            contentType = parameters.get(),
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    // Category Selectable for Pecs
    viewModel {
        SelectCategoriesForPecsViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updateCategoriesUseCase = UpdateCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            stringResourceManager = get<StringResourceManager>()
        )
    }
}