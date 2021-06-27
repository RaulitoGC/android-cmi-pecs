package com.cmi.presentation.pecs.tape

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmi.presentation.CmiActivity
import com.cmi.presentation.R
import com.cmi.presentation.databinding.FragmentTapeBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation
import com.cmi.presentation.model.PictogramModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TapeFragment : Fragment() {

    private var _binding: FragmentTapeBinding? = null
    private val binding get() = _binding!!

    private val tapeViewModel: TapeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTapeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {

        // Toolbar
        lyToolbar.txtTitle.text = getTitle()
        lyToolbar.toolbar.setUpNavigation {
            findNavController().popBackStack()
        }

        imgPlay.setSafeOnClickListener {
            launchSound()
        }
    }

    private fun initViewModel() = with(tapeViewModel) {
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

            if (uiState.launchSound != null) {
                launchSound()
            }
        })
    }

    private fun getTitle(): String{
        val first = getString(R.string.text_app_short_name)
        val second = getString(R.string.text_tape)
        return getString(R.string.text_toolbar_name_format, first, second)
    }

    private fun getStripeText(): String {
        return "${getFirstActionText()} ${getSecondActionText()} ${getMainText()} ${getFirstAttributeText()} ${getSecondAttributeText()}"
    }

    private fun launchSound() {
        val text = getStripeText()
        activity?.let { activity ->
            (activity as CmiActivity).provideTextToSpeechManager().speak(text)
        }
    }

    private fun loadFirstActionPictogram(actionPictogramModel: PictogramModel) = with(binding) {
        cardViewFirstActionPictogram.visibility = View.VISIBLE
        txtFirstActionDescription.text = actionPictogramModel.name
        imgFirstActionPictogram.loadImage(data = actionPictogramModel)
    }

    private fun loadSecondActionPictogram(actionPictogramModel: PictogramModel) = with(binding) {
        cardViewSecondActionPictogram.visibility = View.VISIBLE
        txtSecondActionDescription.text = actionPictogramModel.name
        imgSecondActionPictogram.loadImage(data = actionPictogramModel)
    }

    private fun loadMainPictogram(data: PictogramModel) = with(binding) {
        cardViewMainPictogram.visibility = View.VISIBLE
        txtMainDescription.text = data.name
        imgMainPictogram.loadImage(data = data)
    }

    private fun loadFirstAttributePictogram(data: PictogramModel) = with(binding) {
        cardViewFirstAttributePictogram.visibility = View.VISIBLE
        txtFirstAttributeDescription.text = data.name
        imgFirstAttributePictogram.loadImage(data = data)
    }

    private fun loadSecondAttributePictogram(data: PictogramModel) = with(binding) {
        cardViewSecondAttributePictogram.visibility = View.VISIBLE
        txtSecondAttributeDescription.text = data.name
        imgSecondAttributePictogram.loadImage(data = data)
    }

    private fun getMainText() = binding.txtMainDescription.text.toString()
    private fun getFirstAttributeText() = binding.txtFirstAttributeDescription.text.toString()
    private fun getSecondAttributeText() = binding.txtSecondAttributeDescription.text.toString()
    private fun getSecondActionText() = binding.txtSecondActionDescription.text.toString()
    private fun getFirstActionText() = binding.txtFirstActionDescription.text.toString()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}