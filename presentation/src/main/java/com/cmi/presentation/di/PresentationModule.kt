package com.cmi.presentation.di

import com.cmi.data.di.DataServiceLocator
import com.cmi.data.local.preferences.SurveyPreferences
import com.cmi.domain.usecase.*
import com.cmi.presentation.config.add.category.AddCategoryViewModel
import com.cmi.presentation.config.add.pictogram.AddPictogramViewModel
import com.cmi.presentation.config.delete.category.DeleteCategoryViewModel
import com.cmi.presentation.config.delete.pictogram.DeletePictogramViewModel
import com.cmi.presentation.config.edit.SelectCategoryViewModel
import com.cmi.presentation.config.edit.category.EditCategoryViewModel
import com.cmi.presentation.config.edit.pictogram.EditPictogramViewModel
import com.cmi.presentation.config.edit.pictogram.select.SelectPictogramViewModel
import com.cmi.presentation.config.select.category.SelectCategoryForPecsViewModel
import com.cmi.presentation.config.select.pictogram.SelectPictogramForPecsViewModel
import com.cmi.presentation.intro.SurveyValidator
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
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            getCategoriesUseCase = GetCategoriesUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel { (itemsPerScreen: Int) ->
        AddPictogramViewModel(
            itemsPerScreen = itemsPerScreen,
            getCategoriesUseCase = GetCategoriesUseCase(
                DataServiceLocator.provideSystem(androidContext())
            ),
            addPictogramUseCase = AddPictogramUseCase(
                DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        AddCategoryViewModel(
            addCategoryUseCase = AddCategoryUseCase(DataServiceLocator.provideSystem(androidContext()))
        )
    }

    viewModel {
        SelectCategoryViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        EditCategoryViewModel(
            updateCategoryUseCase = UpdateCategoryUseCase(
                DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        SelectPictogramViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        EditPictogramViewModel(
            updatePictogramUseCase = UpdatePictogramUseCase(
                DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        SelectCategoryForPecsViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            updateCategoriesUseCase = UpdateCategoriesUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        SelectPictogramForPecsViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            updatePictogramsUseCase = UpdatePictogramsUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        DeleteCategoryViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            deleteCategoriesUseCase = DeleteCategoriesUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        DeletePictogramViewModel(
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            deletePictogramsUseCase = DeletePictogramsUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        PictogramViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetPictogramsByCategoryUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            updatePictogramPriorityUseCase = UpdatePictogramPriorityUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            savePictogramPecsIdUseCase = SavePictogramPecsIdUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            ),
            getLastPecsPictogramsUseCase = GetLastPecsPictogramsUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
            )
        )
    }

    viewModel {
        TapeViewModel(
            getLastPecsPictogramsUseCase = GetLastPecsPictogramsUseCase(
                system = DataServiceLocator.provideSystem(androidContext())
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

}