package com.cmi.presentation.pecs.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmi.presentation.R
import com.cmi.presentation.databinding.FragmentCategoryBinding
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.model.CategoryModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryFragment : Fragment(), CategoryAdapter.ItemListener {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val categoryViewModel: CategoryViewModel by viewModel { parametersOf(CategoryAdapter.getItemsPerScreen()) }

    private var categoryAdapter: CategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {

        categoryAdapter = CategoryAdapter()
        categoryAdapter?.setListener(this@CategoryFragment)
        viewPager.adapter = categoryAdapter

        lyToolbar.txtTitle.text = getTitle()
        lyToolbar.toolbar.setUpNavigation {
            findNavController().popBackStack()
        }

        fbBack.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem - 1 >= 0) {
                viewPager.currentItem = currentItem - 1
            }
        }

        fbFront.setOnClickListener {
            val currentItem = viewPager.currentItem
            val size = categoryAdapter?.itemCount ?: 0
            if (currentItem + 1 < size) {
                viewPager.currentItem = currentItem + 1
            }
        }
    }

    private fun getTitle(): String {
        val first = getString(R.string.text_app_short_name)
        val second = getString(R.string.text_categories)
        return getString(R.string.text_toolbar_name_format, first, second)
    }

    private fun initViewModel() = with(categoryViewModel) {
        categories.observe(viewLifecycleOwner) { items ->
            loadCategories(categories = items)
        }
    }

    private fun loadCategories(categories: Map<Int, List<CategoryModel>>) = with(binding) {
        if (categories.isNotEmpty()) {
            categoryAdapter?.setItems(items = categories)
            shimmerContainer.hideShimmer()
            shimmerContainer.visibility = View.GONE
        }
    }

    override fun onItemClick(data: CategoryModel) {
        val directions = CategoryFragmentDirections.actionCategoryFragmentToPictogramFragment(data)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}