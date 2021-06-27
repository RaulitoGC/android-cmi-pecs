package com.cmi.presentation.config.edit.category

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cmi.presentation.R
import com.cmi.presentation.common.PickitFragment
import com.cmi.presentation.config.contract.ChoosePictureContract
import com.cmi.presentation.config.contract.TakePictureContract
import com.cmi.presentation.databinding.FragmentEditPictureBinding
import com.cmi.presentation.ktx.*
import com.cmi.presentation.manager.DexterManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class EditCategoryFragment : PickitFragment() {

    private var _binding: FragmentEditPictureBinding? = null
    private val binding get() = _binding!!

    private val editCategoryViewModel: EditCategoryViewModel by viewModel()
    private val args: EditCategoryFragmentArgs by navArgs()

    private var lastUriPathUploaded: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        lyToolbar.txtTitle.text = getString(R.string.text_update_category)
        lyToolbar.toolbar.setUpNavigation {
            findNavController().popBackStack()
        }
        btnUpdatePicture.text = getString(R.string.text_update_category)
        etName.setText(args.categoryModel.name)
        imgPreview.loadImage(data = args.categoryModel)

        val context = context
        if (context != null) {
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

            btnUpdatePicture.setSafeOnClickListener {
                editCategoryViewModel.updateCategory(
                    name = etName.text,
                    imagePath = if (lastUriPathUploaded.isNullOrEmpty()) args.categoryModel.path else lastUriPathUploaded,
                    isExternal = args.categoryModel.isExternal ?: false,
                    categoryModel = args.categoryModel
                )
            }
        }
    }

    private fun initViewModel() = with(editCategoryViewModel) {
        uiState.observe(viewLifecycleOwner, { uiState ->

            if (uiState.showSuccess != null) {
                showMessage(getString(R.string.text_edit_category_success))
                findNavController().popBackStack()
            }

            if (uiState.showMessage != null) {
                showMessage(getString(uiState.showMessage))
            }
        })
    }

    private fun requestCameraPermission() {
        val context = context
        if (context != null) {
            DexterManager.requestCameraPermission(context = context, listener = {
                requireActivity().takePictureIntent({
                    startActivityForResult(this, TakePictureContract.getRequestCode())
                }) { absolutePath ->
                    editCategoryViewModel.setTakePicturePath(path = absolutePath)
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
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TakePictureContract.getRequestCode() -> {
                    val path = editCategoryViewModel.getTakePicturePath()
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