package com.cmi.presentation.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cmi.presentation.databinding.FragmentSurveyBinding

class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        composeSurveyScreen.setContent {
            SurveyScreen()
        }
//        btnStartSurvey.setSafeOnClickListener {
//            openURL(URL = START_SURVEY_URL)
//        }
//
//        btnEndSurvey.setSafeOnClickListener {
//            openURL(URL = END_SURVEY_URL)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}