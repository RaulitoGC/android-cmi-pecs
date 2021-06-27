package com.cmi.presentation.config.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cmi.presentation.Constants
import com.cmi.presentation.R
import com.cmi.presentation.databinding.FragmentSelectCategoryBinding
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.utils.MarginItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

enum class CategorySelectOptions {
    DELETE_PICTOGRAM,
    SELECT_PICTOGRAM_FOR_PECS,
    EDIT_CATEGORY,
    EDIT_PICTOGRAM
}

class SelectCategoryFragment : Fragment(), SelectCategoryAdapter.ItemListener {

    companion object {
        private const val CATEGORY_SPAN_COUNT = 5
    }

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get() = _binding!!

    private val selectCategoryViewModel: SelectCategoryViewModel by viewModel()
    private val args: SelectCategoryFragmentArgs by navArgs()
    private var adapter: SelectCategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        val context = context
        if (context != null) {
            lyToolbar.txtTitle.text = when (args.categorySelectOptions) {
                CategorySelectOptions.SELECT_PICTOGRAM_FOR_PECS -> {
                    getString(R.string.text_select_pictogram_for_pecs)
                }

                CategorySelectOptions.EDIT_PICTOGRAM -> {
                    getString(R.string.text_edit_pictogram)
                }

                CategorySelectOptions.EDIT_CATEGORY -> {
                    getString(R.string.text_edit_category)
                }

                CategorySelectOptions.DELETE_PICTOGRAM -> {
                    val pictogramName = getString(R.string.text_pictogram)
                    getString(R.string.text_delete_format, pictogramName)
                }
            }

            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }

            adapter = SelectCategoryAdapter()
            adapter?.setListener(this@SelectCategoryFragment)

            val layoutManager = GridLayoutManager(context, CATEGORY_SPAN_COUNT)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(MarginItemDecorator(context = context, marginInDp = R.dimen.margin_4dp))
            recyclerView.adapter = adapter
        }
    }

    private fun initViewModel() = with(selectCategoryViewModel) {
        categories.observe(viewLifecycleOwner, { categories ->
            showCategories(categories)
        })
        getCategories()
    }

    private fun showCategories(categories: List<CategoryModel>){
        if(categories.isNotEmpty()){
            adapter?.setItems(items = categories)
            hideShimmer()
        }
    }

    private fun hideShimmer() = with(binding){
        shimmerContainer.hideShimmer()
        shimmerContainer.visibility = View.GONE
    }

    override fun onItemClick(data: CategoryModel) {
        val directions = when (args.categorySelectOptions) {
            CategorySelectOptions.SELECT_PICTOGRAM_FOR_PECS -> {
                SelectCategoryFragmentDirections.actionSelectCategoryFragmentToSelectPictogramForPecsFragment(
                    categoryModel = data
                )
            }

            CategorySelectOptions.EDIT_PICTOGRAM -> {
                SelectCategoryFragmentDirections.actionSelectCategoryFragmentToSelectPictogramFragment(
                    categoryModel = data
                )
            }

            CategorySelectOptions.EDIT_CATEGORY -> {
                SelectCategoryFragmentDirections.actionSelectCategoryFragmentToEditCategoryFragment(
                    categoryModel = data
                )
            }

            CategorySelectOptions.DELETE_PICTOGRAM -> {
                SelectCategoryFragmentDirections.actionSelectCategoryFragmentToDeletePictogramFragment(
                    categoryModel = data
                )
            }
        }

        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}