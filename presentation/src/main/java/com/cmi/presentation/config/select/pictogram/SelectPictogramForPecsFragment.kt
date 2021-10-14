package com.cmi.presentation.config.select.pictogram

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
import com.cmi.presentation.config.select.category.SelectCategoryForPecsViewModel
import com.cmi.presentation.databinding.FragmentSelectBinding
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.ktx.showAlertMessage
import com.cmi.presentation.ktx.showMessage
import com.cmi.presentation.model.PictogramSelectableModel
import com.cmi.presentation.utils.MarginItemDecorator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SelectPictogramForPecsFragment : BaseFragment(), SelectablePictogramAdapter.ItemListener {

    companion object {
        private const val SELECT_PICTOGRAM_SPAN_COUNT = 5
    }

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    private var selectablPictogramAdapter: SelectablePictogramAdapter? = null
    private val args: SelectPictogramForPecsFragmentArgs by navArgs()
    private lateinit var selectPictogramForPecsViewModel: SelectPictogramForPecsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this@SelectPictogramForPecsFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectPictogramForPecsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SelectPictogramForPecsViewModel::class.java)
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
            lyToolbar.txtTitle.text = getString(R.string.text_select_pictogram_for_pecs)
            lyToolbar.txtAction.text = getString(R.string.text_update)
            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }

            txtSelectDescription.text =
                getString(R.string.text_select_pictogram_for_pecs_description)

            selectablPictogramAdapter = SelectablePictogramAdapter()
            selectablPictogramAdapter?.setListener(this@SelectPictogramForPecsFragment)

            val layoutManager = GridLayoutManager(context, SELECT_PICTOGRAM_SPAN_COUNT)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                MarginItemDecorator(context = context, marginInDp = R.dimen.margin_4dp)
            )
            recyclerView.adapter = selectablPictogramAdapter

            lyToolbar.txtAction.setSafeOnClickListener {
                selectPictogramForPecsViewModel.updatePictogramsForPecs(selectablPictogramAdapter?.getItems().orEmpty())
            }
        }
    }

    private fun initViewModel() = with(selectPictogramForPecsViewModel) {

        pictograms.observe(viewLifecycleOwner, { items ->
            setupToolbar(itemsSelected = items.size)
            showItems(list = items)
        })

        uiState.observe(viewLifecycleOwner, { uiState ->

            if (uiState.showError != null) {
                showMessage(getString(R.string.text_generic_error))
            }

            if (uiState.showSuccess != null) {
                showMessage(getString(R.string.text_select_pictograms_success))
                findNavController().popBackStack()
            }
        })

        getPictogramsByCategory(args.categoryModel)
    }

    override fun onItemClick(data: PictogramSelectableModel) {
        val totalSelectedItems = selectablPictogramAdapter?.selectedItems?.size ?: 0
        setupToolbar(itemsSelected = totalSelectedItems)
        selectablPictogramAdapter?.updateItem(data = data)
    }

    private fun showItems(list: List<PictogramSelectableModel>) {
        if (list.isNotEmpty()) {
            selectablPictogramAdapter?.setItems(items = list)
        } else {
            showAlertMessage(
                message = getString(R.string.text_message_empty_pictograms_for_pecs),
                listener = {
                    findNavController().popBackStack()
                }
            )
        }
        binding.shimmerContainer.hideShimmer()
        binding.shimmerContainer.visibility = View.GONE
    }

    private fun setupToolbar(itemsSelected: Int) {
        var title = getString(R.string.text_select_pictogram_for_pecs)
        if (itemsSelected > 0) {
            title += getString(
                R.string.text_select_pictograms_size_format,
                itemsSelected.toString()
            )
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