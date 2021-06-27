package com.cmi.presentation.intro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmi.presentation.Constants.SURVEY_URL
import com.cmi.presentation.Constants.YOUTUBE_GUIDE
import com.cmi.presentation.R
import com.cmi.presentation.databinding.FragmentIntroBinding
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.showAlertMessage
import org.koin.android.ext.android.inject

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    private val surveyValidator: SurveyValidator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {

        if(surveyValidator.showSurveyAlert()){
            showAlertMessage(
                title = surveyValidator.getSurveyTitle(),
                message = surveyValidator.getSurveyDescription(),
                positiveButton = getString(R.string.text_survey),
                negativeButton = getString(R.string.text_remember_survey_later),
                listener = {
                    surveyValidator.registerSurveyButtonClicked()
                    openURL(SURVEY_URL)
                }
            )
        }

        btnStart.setSafeOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_categoryFragment)
        }

        btnGuide.setSafeOnClickListener {
            openURL(YOUTUBE_GUIDE)
        }

        imgSettings.setSafeOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_configurationFragment)
        }
    }

    private fun openURL(url : String){
        val context = context
        if(context != null){
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if(intent.resolveActivity(context.packageManager) != null){
                startActivity(intent)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}