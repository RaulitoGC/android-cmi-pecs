package com.cmi.presentation.config.add.pictogram

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cmi.presentation.R
import com.cmi.presentation.common.PickitFragment
import com.cmi.presentation.config.contract.ChoosePictureContract
import com.cmi.presentation.config.contract.TakePictureContract
import com.cmi.presentation.databinding.FragmentAddPictogramBinding
import com.cmi.presentation.ktx.*
import com.cmi.presentation.manager.DexterManager
import com.cmi.presentation.model.CategorySelectableModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class AddPictogramFragment : PickitFragment(), CategoryAdapter.ItemListener {

    private var _binding: FragmentAddPictogramBinding? = null
    private val binding get() = _binding!!

    private val addPictogramViewModel: AddPictogramViewModel by viewModel {
        parametersOf(
            CategoryAdapter.getItemsPerScreen()
        )
    }
    private var categoryAdapter: CategoryAdapter? = null
    private var lastUriPathUploaded: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPictogramBinding.inflate(inflater, container, false)
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

            lyToolbar.txtTitle.text = getString(R.string.text_add_pictogram)
            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }

            if (TakePictureContract.resolveActivity(context)) {
                imgCamera.visibility = View.VISIBLE
                txtCameraLabel.visibility = View.VISIBLE
                imgCamera.setSafeOnClickListener {
                    requestCameraPermission()
                }
            } else {
                imgCamera.visibility = View.GONE
                txtCameraLabel.visibility = View.GONE
            }

            Timber.d(" chooser picture -> ${ChoosePictureContract.resolveActivity(context.applicationContext)}")

            if (ChoosePictureContract.resolveActivity(context)) {
                imgGallery.visibility = View.VISIBLE
                txtGalleryLabel.visibility = View.VISIBLE
                imgGallery.setSafeOnClickListener {
                    requestStoragePermission()
                }
            } else {
                imgGallery.visibility = View.GONE
                txtGalleryLabel.visibility = View.GONE
            }

            if (!ChoosePictureContract.resolveActivity(context) &&
                !TakePictureContract.resolveActivity(context)
            ) {
                cardViewPreview.visibility = View.GONE
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

            btnAddPictogram.setSafeOnClickListener {
                addPictogramViewModel.addPictogram(
                    pictogramName = etName.text,
                    pictureFileName = lastUriPathUploaded,
                    categorySelected = categoryAdapter?.getCategorySelected()
                )
            }
        }
    }

    private fun initViewModel() = with(addPictogramViewModel) {
        categories.observe(viewLifecycleOwner, { items ->
            loadPictograms(categories = items)
        })

        showMessage.observe(viewLifecycleOwner, { resource ->
            showMessage(getString(resource))
        })

        showSuccess.observe(viewLifecycleOwner, { success ->
            if (success) {
                showMessage(getString(R.string.text_pictogram_added_success))
                findNavController().popBackStack()
            } else {
                showMessage(getString(R.string.text_generic_error))
            }
        })
    }

    private fun loadPictograms(categories: Map<Int, List<CategorySelectableModel>>) =
        with(binding) {
            if (categories.isNotEmpty()) {
                categoryAdapter = CategoryAdapter(categories)
                categoryAdapter?.setListener(this@AddPictogramFragment)
                viewPager.adapter = categoryAdapter
            }
        }

    private fun requestCameraPermission() {
        val context = context
        if (context != null) {
            DexterManager.requestCameraPermission(context = context, listener = {
                requireActivity().takePictureIntent({
                    startActivityForResult(this, TakePictureContract.getRequestCode())
                }) { absolutePath ->
                    addPictogramViewModel.setTakePicturePath(path = absolutePath)
                }
            })
        }
    }

    private fun requestStoragePermission() {
        val context = context
        if (context != null) {
            DexterManager.requestStoragePermissions(context = context, listener = {
                requireActivity().selectFileIntent({
                    startActivityForResult(this, ChoosePictureContract.getRequestCode())
                }, mimeTypes = ChoosePictureContract.getPictureMimeTypes())
            })
        }
    }

    override fun onItemClick(adapterPosition: Int, data: CategorySelectableModel) {
        categoryAdapter?.updateSelectedItem(
            adapterPosition = adapterPosition,
            categorySelectableModel = data
        )
    }

    override fun onStartLoadingImage() {
        val context = context
        if (context != null) {
            binding.imgPreview.setBackgroundColor(
                ContextCompat.getColor(context, R.color.windowBackground)
            )
            binding.progressIndicator.visibility = View.VISIBLE
        }
    }

    override fun onProgressUpdate(progress: Int) {
        binding.progressIndicator.progress = progress
    }

    override fun onImageLoaded(wasSuccessful: Boolean, path: String?) {
        val context = context
        if (context != null) {
            binding.progressIndicator.visibility = View.GONE
            if (wasSuccessful) {
                lastUriPathUploaded = path
                binding.imgPreview.background = null
                Glide.with(context).load(path).into(binding.imgPreview)
            } else {
                binding.imgPreview.background =
                    ContextCompat.getDrawable(context, R.drawable.ic_image_preview)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TakePictureContract.getRequestCode() -> {
                    val path = addPictogramViewModel.getTakePicturePath()
                    onImageLoaded(wasSuccessful = path.isNotEmpty(), path = path)
                }
                ChoosePictureContract.getRequestCode() -> {
                    data?.data?.let { uri -> getPickiT().getPath(uri, Build.VERSION.SDK_INT) }
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}