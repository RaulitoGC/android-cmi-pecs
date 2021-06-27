package com.cmi.presentation.config.select.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cmi.presentation.R
import com.cmi.presentation.databinding.FragmentSelectBinding
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.ktx.showAlertMessage
import com.cmi.presentation.ktx.showMessage
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.utils.MarginItemDecorator
import org.koin.android.ext.android.inject

class SelectCategoryForPecsFragment : Fragment(), SelectableCategoryAdapter.ItemListener {

    companion object {
        private const val SELECT_CATEGORY_SPAN_COUNT = 5
    }

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    private var selectableCategoryAdapter: SelectableCategoryAdapter? = null
    private val selectCategoryForPecsViewModel: SelectCategoryForPecsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
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
            lyToolbar.txtTitle.text = getString(R.string.text_select_category_for_pecs)
            lyToolbar.txtAction.text = getString(R.string.text_update)
            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }

            txtSelectDescription.text =
                getString(R.string.text_select_category_for_pecs_description)

            selectableCategoryAdapter = SelectableCategoryAdapter()
            selectableCategoryAdapter?.setListener(this@SelectCategoryForPecsFragment)

            val layoutManager = GridLayoutManager(context, SELECT_CATEGORY_SPAN_COUNT)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                MarginItemDecorator(context = context, marginInDp = R.dimen.margin_4dp)
            )
            recyclerView.adapter = selectableCategoryAdapter

            lyToolbar.txtAction.setSafeOnClickListener {
                selectCategoryForPecsViewModel.updateCategoriesForPecs(selectableCategoryAdapter?.getItems().orEmpty())
            }
        }
    }

    private fun initViewModel() = with(selectCategoryForPecsViewModel) {

        categories.observe(viewLifecycleOwner, { items ->
            setupToolbar(itemsSelected = items.size)
            showItems(list = items)
        })

        uiState.observe(viewLifecycleOwner, { uiState ->

            if (uiState.showError != null) {
                showMessage(getString(R.string.text_generic_error))
            }

            if (uiState.showSuccess != null) {
                showMessage(getString(R.string.text_select_categories_success))
                findNavController().popBackStack()
            }
        })
    }

    override fun onItemClick(data: CategorySelectableModel) {
        val totalSelectedItems = selectableCategoryAdapter?.selectedItems?.size ?: 0
        setupToolbar(itemsSelected = totalSelectedItems)
        selectableCategoryAdapter?.updateItem(data = data)
    }

    private fun showItems(list: List<CategorySelectableModel>) {
        if (list.isNotEmpty()) {
            selectableCategoryAdapter?.setItems(items = list)
            binding.shimmerContainer.hideShimmer()
            binding.shimmerContainer.visibility = View.GONE
        } else {
            showAlertMessage(
                message = getString(R.string.text_message_empty_categories_for_pecs),
                listener = {
                    findNavController().popBackStack()
                }
            )
        }
    }

    private fun setupToolbar(itemsSelected: Int) {
        var title = getString(R.string.text_select_category_for_pecs)
        if (itemsSelected > 0) {
            title += " ${
                getString(
                    R.string.text_select_category_size_format,
                    itemsSelected.toString()
                )
            }"
            binding.lyToolbar.txtAction.isEnabled = true
        } else {
            binding.lyToolbar.txtAction.isEnabled = false
        }
        binding.lyToolbar.txtTitle.text = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}