package com.cmi.presentation.di.viewmodel

import androidx.lifecycle.ViewModel
import com.cmi.presentation.config.add.category.AddCategoryViewModel
import com.cmi.presentation.config.add.pictogram.AddPictogramViewModel
import com.cmi.presentation.config.delete.category.DeleteCategoryViewModel
import com.cmi.presentation.config.delete.pictogram.DeletePictogramViewModel
import com.cmi.presentation.config.edit.SelectCategoryViewModel
import com.cmi.presentation.config.edit.category.EditCategoryViewModel
import com.cmi.presentation.config.edit.pictogram.EditPictogramViewModel
import com.cmi.presentation.config.edit.pictogram.select.SelectPictogramViewModel
import com.cmi.presentation.config.select.category.SelectCategoryForPecsViewModel
import com.cmi.presentation.pecs.category.CategoryViewModel
import com.cmi.presentation.pecs.pictogram.PictogramViewModel
import com.cmi.presentation.pecs.tape.TapeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun categoryViewModel(categoryViewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPictogramViewModel::class)
    abstract fun addPictogramViewModel(addPictogramViewModel: AddPictogramViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCategoryViewModel::class)
    abstract fun addCategoryViewModel(addCategoryViewModel: AddCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCategoryViewModel::class)
    abstract fun selectCategoryViewModel(selectCategoryViewModel: SelectCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditCategoryViewModel::class)
    abstract fun editCategoryViewModel(editCategoryViewModel: EditCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectPictogramViewModel::class)
    abstract fun selectPictogramViewModel(selectPictogramViewModel: SelectPictogramViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditPictogramViewModel::class)
    abstract fun editPictogramViewModel(editPictogramViewModel: EditPictogramViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCategoryForPecsViewModel::class)
    abstract fun selectCategoryForPecsViewModel(selectCategoryForPecsViewModel: SelectCategoryForPecsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeleteCategoryViewModel::class)
    abstract fun deleteCategoryViewModel(deleteCategoryViewModel: DeleteCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeletePictogramViewModel::class)
    abstract fun deletePictogramViewModel(deletePictogramViewModel: DeletePictogramViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PictogramViewModel::class)
    abstract fun pictogramViewModel(pictogramViewModel: PictogramViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TapeViewModel::class)
    abstract fun tapeViewModel(tapeViewModel: TapeViewModel): ViewModel
}