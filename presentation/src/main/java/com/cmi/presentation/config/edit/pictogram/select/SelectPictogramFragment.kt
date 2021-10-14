package com.cmi.presentation.config.edit.pictogram.select

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cmi.presentation.R
import com.cmi.presentation.common.BaseFragment
import com.cmi.presentation.config.edit.category.EditCategoryViewModel
import com.cmi.presentation.databinding.FragmentSelectPictogramBinding
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.utils.MarginItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectPictogramFragment : BaseFragment(), SelectPictogramAdapter.ItemListener {

    companion object {
        private const val CATEGORY_SPAN_COUNT = 5
    }

    private var _binding: FragmentSelectPictogramBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectPictogramViewModel: SelectPictogramViewModel
    private val args: SelectPictogramFragmentArgs by navArgs()
    private var adapter: SelectPictogramAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this@SelectPictogramFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectPictogramViewModel =
            ViewModelProvider(this, viewModelFactory).get(SelectPictogramViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectPictogramBinding.inflate(inflater, container, false)
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
            shimmerContainer.startShimmer()
            lyToolbar.txtTitle.text = getString(R.string.text_edit_pictogram)
            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }

            adapter = SelectPictogramAdapter()
            adapter?.setListener(this@SelectPictogramFragment)

            val layoutManager = GridLayoutManager(context, CATEGORY_SPAN_COUNT)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                MarginItemDecorator(context = context, marginInDp = R.dimen.margin_4dp)
            )
            recyclerView.adapter = adapter
        }
    }

    private fun initViewModel() = with(selectPictogramViewModel) {
        pictograms.observe(viewLifecycleOwner, { pictograms ->
            showPictograms(pictograms)
        })
        getPictogramsByCategory(args.categoryModel)
    }

    private fun showPictograms(pictograms: List<PictogramModel>){
        if(pictograms.isNotEmpty()) {
            adapter?.setItems(items = pictograms)
            hideShimmer()
        }
    }

    private fun hideShimmer() = with(binding){
        shimmerContainer.hideShimmer()
        shimmerContainer.visibility = View.GONE
    }

    override fun onItemClick(data: PictogramModel) {
        val directions =
            SelectPictogramFragmentDirections.actionSelectPictogramFragmentToEditPictogramFragment(
                pictogramModel = data
            )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}