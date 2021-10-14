package com.cmi.presentation.di.fragment

import androidx.fragment.app.Fragment
import com.cmi.data.di.DataModule
import com.cmi.domain.di.UseCaseModule
import com.cmi.presentation.config.ConfigurationFragment
import com.cmi.presentation.config.add.category.AddCategoryFragment
import com.cmi.presentation.config.add.pictogram.AddPictogramFragment
import com.cmi.presentation.config.delete.category.DeleteCategoryFragment
import com.cmi.presentation.config.delete.pictogram.DeletePictogramFragment
import com.cmi.presentation.config.edit.SelectCategoryFragment
import com.cmi.presentation.config.edit.category.EditCategoryFragment
import com.cmi.presentation.config.edit.pictogram.EditPictogramFragment
import com.cmi.presentation.config.edit.pictogram.select.SelectPictogramFragment
import com.cmi.presentation.config.select.category.SelectCategoryForPecsFragment
import com.cmi.presentation.config.select.pictogram.SelectPictogramForPecsFragment
import com.cmi.presentation.di.activity.ActivityComponent
import com.cmi.presentation.di.viewmodel.ViewModelModule
import com.cmi.presentation.intro.IntroFragment
import com.cmi.presentation.pecs.category.CategoryFragment
import com.cmi.presentation.pecs.pictogram.PictogramFragment
import com.cmi.presentation.pecs.tape.TapeFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ViewModelModule::class, UseCaseModule::class, DataModule::class])
interface FragmentComponent {

    fun inject(addCategoryFragment: AddCategoryFragment)
    fun inject(addPictogramFragment: AddPictogramFragment)
    fun inject(deleteCategoryFragment: DeleteCategoryFragment)
    fun inject(deletePictogramFragment: DeletePictogramFragment)
    fun inject(editCategoryFragment: EditCategoryFragment)
    fun inject(selectPictogramFragment: SelectPictogramFragment)
    fun inject(editPictogramFragment: EditPictogramFragment)
    fun inject(selectCategoryFragment: SelectCategoryFragment)
    fun inject(selectCategoryForPecsFragment: SelectCategoryForPecsFragment)
    fun inject(selectPictogramForPecsFragment: SelectPictogramForPecsFragment)
    fun inject(configurationFragment: ConfigurationFragment)
    fun inject(categoryFragment: CategoryFragment)
    fun inject(pictogramFragment: PictogramFragment)
    fun inject(tapeFragment: TapeFragment)

}