package com.cmi.presentation.config.add.category

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
import com.cmi.presentation.components.category.CategorySelectable
import com.cmi.presentation.config.add.PictureLoader
import com.cmi.presentation.config.add.model.PictureLoaderContentType
import com.cmi.presentation.config.contract.ChoosePictureContract
import com.cmi.presentation.config.contract.TakePictureContract
import com.cmi.presentation.databinding.FragmentAddCategoryBinding
import com.cmi.presentation.ktx.*
import com.cmi.presentation.manager.DexterManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AddCategoryFragment : PickitFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    private val addCategoryViewModel: AddCategoryViewModel by viewModel()

    private var lastUriPathUploaded: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
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
            pictureLoaderScreen.setContent {
                PictureLoader(PictureLoaderContentType.CategoryImage)
            }
//            lyToolbar.txtTitle.text = getString(R.string.text_add_category)
//            lyToolbar.toolbar.setUpNavigation {
//                findNavController().popBackStack()
//            }
//
//            if (TakePictureContract.resolveActivity(context)) {
//                imgCamera.visibility = View.VISIBLE
//                txtCameraLabel.visibility = View.VISIBLE
//                imgCamera.setSafeOnClickListener {
//                    requestCameraPermission()
//                }
//            } else {
//                imgCamera.visibility = View.GONE
//                txtCameraLabel.visibility = View.GONE
//            }
//
//            if (ChoosePictureContract.resolveActivity(context)) {
//                imgGallery.visibility = View.VISIBLE
//                txtGalleryLabel.visibility = View.VISIBLE
//                imgGallery.setSafeOnClickListener {
//                    requestStoragePermission()
//                }
//            } else {
//                imgGallery.visibility = View.GONE
//                txtGalleryLabel.visibility = View.GONE
//            }
//
//            if (!ChoosePictureContract.resolveActivity(context)
//                && !TakePictureContract.resolveActivity(context)
//            ) {
//                cardViewPreview.visibility = View.GONE
//            }
//
//            btnAddCategory.setSafeOnClickListener {
//                addCategoryViewModel.addCategory(
//                    pictogramName = etName.text.toString(),
//                    pictureFileName = lastUriPathUploaded
//                )
//            }
        }
    }

    private fun initViewModel() = with(addCategoryViewModel) {

        showMessage.observe(viewLifecycleOwner) { resource ->
            showMessage(getString(resource))
        }

        showSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                showMessage(getString(R.string.text_category_add_success))
                findNavController().popBackStack()
            } else {
                showMessage(getString(R.string.text_generic_error))
            }
        }
    }

    private fun requestCameraPermission() {
        val context = context
        if (context != null) {
            DexterManager.requestCameraPermission(context = context, listener = {
                requireActivity().takePictureIntent({
                    startActivityForResult(this, TakePictureContract.getRequestCode())
                }) { absolutePath ->
                    addCategoryViewModel.setTakePicturePath(path = absolutePath)
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

    override fun onStartLoadingImage() {
//        val context = context
//        if (context != null) {
//            binding.imgPreview.setBackgroundColor(
//                ContextCompat.getColor(context, R.color.windowBackground)
//            )
//            binding.progressIndicator.visibility = View.VISIBLE
//        }
    }

    override fun onProgressUpdate(progress: Int) {
//        binding.progressIndicator.progress = progress
    }

    override fun onImageLoaded(wasSuccessful: Boolean, path: String?) {
//        val context = context
//        if (context != null) {
//            binding.progressIndicator.visibility = View.GONE
//            if (wasSuccessful) {
//                lastUriPathUploaded = path
//                binding.imgPreview.background = null
//                Glide.with(context).load(path).into(binding.imgPreview)
//            } else {
//                binding.imgPreview.background =
//                    ContextCompat.getDrawable(context, R.drawable.ic_image_preview)
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TakePictureContract.getRequestCode() -> {
                    val path = addCategoryViewModel.getTakePicturePath()
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