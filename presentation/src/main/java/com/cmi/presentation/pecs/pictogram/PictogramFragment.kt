package com.cmi.presentation.pecs.pictogram

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmi.domain.usecase.GetLastPecsPictogramsUseCase
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.presentation.R
import com.cmi.presentation.common.BaseFragment
import com.cmi.presentation.databinding.FragmentPictogramBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.pecs.category.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PictogramFragment : BaseFragment(), PictogramAdapter.ItemListener {

    private var _binding: FragmentPictogramBinding? = null
    private val binding get() = _binding!!

    private val args: PictogramFragmentArgs by navArgs()
    private lateinit var pictogramViewModel: PictogramViewModel
    private var pictogramAdapter: PictogramAdapter? = null

    private var setupAttributeFlag: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this@PictogramFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pictogramViewModel =
            ViewModelProvider(this, viewModelFactory).get(PictogramViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictogramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        // Toolbar
        lyToolbar.txtTitle.text = getTitle()
        lyToolbar.toolbar.setUpNavigation {
            findNavController().popBackStack()
        }

        //ViewPager
        pictogramAdapter = PictogramAdapter()
        pictogramAdapter?.setListener(this@PictogramFragment)

        viewPager.adapter = pictogramAdapter

        fbBack.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem - 1 >= 0) {
                viewPager.currentItem = currentItem - 1
            }
        }

        fbFront.setOnClickListener {
            val currentItem = viewPager.currentItem
            val size = pictogramAdapter?.itemCount ?: 0
            if (currentItem + 1 < size) {
                viewPager.currentItem = currentItem + 1
            }
        }

        imgCloseFirstActionPictogram.setSafeOnClickListener {
            setFirstActionPictogramVisibility(visibility = View.INVISIBLE)
            pictogramViewModel.removeFirstActionPictogram()
        }

        imgCloseSecondActionPictogram.setSafeOnClickListener {
            setSecondActionPictogramVisibility(visibility = View.INVISIBLE)
            pictogramViewModel.removeSecondActionPictogram()
        }

        imgCloseMainPictogram.setSafeOnClickListener {
            setMainPictogramVisibility(visibility = View.INVISIBLE)
            pictogramViewModel.removeMainPictogram()
        }

        imgCloseFirstAttributePictogram.setSafeOnClickListener {
            setFirstAttributePictogramVisibility(visibility = View.INVISIBLE)
            pictogramViewModel.removeFirstAttributePictogram()
            setupAttributeFlag = false
        }

        imgCloseSecondAttributePictogram.setSafeOnClickListener {
            setSecondAttributePictogramVisibility(visibility = View.INVISIBLE)
            pictogramViewModel.removeSecondAttributePictogram()
        }

        imgPlay.setSafeOnClickListener {
            if (launchStripScreen()) {
                findNavController().navigate(R.id.action_pictogramFragment_to_tapeFragment)
            }
        }
    }

    private fun initViewModel() = with(pictogramViewModel) {
        pictograms.observe(viewLifecycleOwner, { items ->
            pictogramAdapter?.setItems(items = items)
        })

        uiState.observe(viewLifecycleOwner, { uiState ->

            if (uiState.actionFirstPictogramModel != null) {
                loadFirstActionPictogram(uiState.actionFirstPictogramModel)
            }

            if (uiState.actionSecondPictogramModel != null) {
                loadSecondActionPictogram(uiState.actionSecondPictogramModel)
            }

            if (uiState.mainPictogramModel != null) {
                loadMainPictogram(uiState.mainPictogramModel)
            }

            if (uiState.attributeFirstPictogramModel != null) {
                loadFirstAttributePictogram(uiState.attributeFirstPictogramModel)
            }

            if (uiState.attributeSecondPictogramModel != null) {
                loadSecondAttributePictogram(uiState.attributeSecondPictogramModel)
            }
        })

        getPictogramsByCategory(args.categoryModel)
        loadInformation()
    }

    override fun onItemClick(data: PictogramModel) {
        pictogramViewModel.updatePictogramPriority(pictogramModel = data)
        when {
            GetPictogramsByCategoryUseCase.isAction(data.categoryId) -> {
                if (GetLastPecsPictogramsUseCase.isWantPictogram(data.pictogramId ?: -1)) {
                    loadFirstActionPictogram(data = data)
                    pictogramViewModel.saveFirstActionPictogram(pictogramModel = data)
                } else {
                    loadSecondActionPictogram(data = data)
                    pictogramViewModel.saveSecondActionPictogram(pictogramModel = data)
                }
            }

            GetPictogramsByCategoryUseCase.isAttribute(data.categoryId) -> {
                when {
                    isFirstAttributeAvailable() -> {
                        loadFirstAttributePictogram(data = data)
                        pictogramViewModel.saveFirstAttributePictogram(pictogramModel = data)
                    }
                    else -> {
                        loadSecondAttributePictogram(data = data)
                        pictogramViewModel.saveSecondAttributePictogram(pictogramModel = data)
                    }
                }
            }
            else -> {
                loadMainPictogram(data = data)
                pictogramViewModel.saveMainPictogram(pictogramModel = data)
            }
        }
    }

    private fun getTitle(): String{
        val first = getString(R.string.text_app_short_name)
        val second = getString(R.string.text_pictograms)
        return getString(R.string.text_toolbar_name_format, first, second)
    }

    private fun loadFirstActionPictogram(data: PictogramModel) = with(binding) {
        setFirstActionPictogramVisibility(visibility = View.VISIBLE)
        txtFirstActionDescription.text = data.name
        imgFirstActionPictogram.loadImage(data = data)
    }

    private fun loadSecondActionPictogram(data: PictogramModel) = with(binding) {
        setSecondActionPictogramVisibility(visibility = View.VISIBLE)
        txtSecondActionDescription.text = data.name
        imgSecondActionPictogram.loadImage(data = data)
    }

    private fun loadMainPictogram(data: PictogramModel) = with(binding) {
        setMainPictogramVisibility(visibility = View.VISIBLE)
        txtMainDescription.text = data.name
        imgMainPictogram.loadImage(data = data)
    }

    private fun loadFirstAttributePictogram(data: PictogramModel) = with(binding) {
        setFirstAttributePictogramVisibility(visibility = View.VISIBLE)
        txtFirstAttributeDescription.text = data.name
        imgFirstAttributePictogram.loadImage(data = data)
    }

    private fun loadSecondAttributePictogram(data: PictogramModel) = with(binding) {
        setSecondAttributePictogramVisibility(visibility = View.VISIBLE)
        txtSecondAttributeDescription.text = data.name
        imgSecondAttributePictogram.loadImage(data = data)
    }

    private fun setFirstActionPictogramVisibility(visibility: Int) {
        binding.cardViewFirstActionPictogram.visibility = visibility
        binding.imgCloseFirstActionPictogram.visibility = visibility
    }

    private fun setSecondActionPictogramVisibility(visibility: Int) {
        binding.cardViewSecondActionPictogram.visibility = visibility
        binding.imgCloseSecondActionPictogram.visibility = visibility
    }

    private fun setMainPictogramVisibility(visibility: Int) {
        binding.cardViewMainPictogram.visibility = visibility
        binding.imgCloseMainPictogram.visibility = visibility
    }

    private fun setFirstAttributePictogramVisibility(visibility: Int) {
        binding.cardViewFirstAttributePictogram.visibility = visibility
        binding.imgCloseFirstAttributePictogram.visibility = visibility
    }

    private fun setSecondAttributePictogramVisibility(visibility: Int) {
        binding.cardViewSecondAttributePictogram.visibility = visibility
        binding.imgCloseSecondAttributePictogram.visibility = visibility
    }

    private fun launchStripScreen(): Boolean = with(binding) {
        return cardViewFirstActionPictogram.isVisible || cardViewSecondActionPictogram.isVisible || cardViewMainPictogram.isVisible || cardViewFirstAttributePictogram.isVisible || cardViewSecondAttributePictogram.isVisible
    }

    private fun isFirstAttributeAvailable(): Boolean {
        return !binding.cardViewFirstAttributePictogram.isVisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}