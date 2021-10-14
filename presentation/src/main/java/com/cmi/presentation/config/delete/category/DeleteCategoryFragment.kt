package com.cmi.presentation.config.delete.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cmi.presentation.R
import com.cmi.presentation.common.BaseFragment
import com.cmi.presentation.config.select.category.SelectableCategoryAdapter
import com.cmi.presentation.databinding.FragmentSelectBinding
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.ktx.showAlertMessage
import com.cmi.presentation.ktx.showMessage
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.utils.MarginItemDecorator

class DeleteCategoryFragment : BaseFragment(), SelectableCategoryAdapter.ItemListener {

    companion object {
        private const val SELECT_CATEGORY_SPAN_COUNT = 5
    }

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    private var selectableCategoryAdapter: SelectableCategoryAdapter? = null
    private lateinit var deleteCategoryViewModel: DeleteCategoryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this@DeleteCategoryFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteCategoryViewModel =
            ViewModelProvider(this, viewModelFactory).get(DeleteCategoryViewModel::class.java)
    }

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
            lyToolbar.txtTitle.text =
                getString(R.string.text_delete_format, getString(R.string.text_category))
            lyToolbar.txtAction.text = getString(R.string.text_delete)
            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }

            txtSelectDescription.text =
                getString(R.string.text_delete_categories_description)

            selectableCategoryAdapter = SelectableCategoryAdapter()
            selectableCategoryAdapter?.setListener(this@DeleteCategoryFragment)

            val layoutManager = GridLayoutManager(context, SELECT_CATEGORY_SPAN_COUNT)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                MarginItemDecorator(context = context, marginInDp = R.dimen.margin_4dp)
            )
            recyclerView.adapter = selectableCategoryAdapter

            lyToolbar.txtAction.setSafeOnClickListener {
                deleteCategoryViewModel.deleteCategories(selectableCategoryAdapter?.selectedItems.orEmpty())
            }
        }
    }

    private fun initViewModel() = with(deleteCategoryViewModel) {
        categories.observe(viewLifecycleOwner, { items ->
            setupToolbar(itemsSelected = 0)
            showItems(items = items)
        })

        uiState.observe(viewLifecycleOwner, { uiState ->

            if (uiState.showError != null) {
                showMessage(getString(R.string.text_generic_error))
            }

            if (uiState.showSuccess != null) {
                showMessage(getString(R.string.text_delete_categories_success))
                findNavController().popBackStack()
            }
        })

        getExternalCategories()
    }

    private fun showItems(items: List<CategorySelectableModel>) {
        if (items.isNotEmpty()) {
            selectableCategoryAdapter?.setItems(items = items)
        } else {
            showAlertMessage(
                message = getString(R.string.text_message_empty_categories),
                listener = {
                    findNavController().popBackStack()
                }
            )
        }
        binding.shimmerContainer.hideShimmer()
        binding.shimmerContainer.visibility = View.GONE
    }

    override fun onItemClick(data: CategorySelectableModel) {
        val totalSelectedItems = selectableCategoryAdapter?.selectedItems?.size ?: 0
        setupToolbar(itemsSelected = totalSelectedItems)
        selectableCategoryAdapter?.updateItem(data = data)
    }

    private fun setupToolbar(itemsSelected: Int) {
        var title = getString(R.string.text_delete_format, getString(R.string.text_category))
        if (itemsSelected > 0) {
            title += getString(R.string.text_select_category_size_format, itemsSelected.toString())
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