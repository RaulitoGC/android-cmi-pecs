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
import com.cmi.presentation.ktx.openURL
import com.cmi.presentation.ktx.setSafeOnClickListener

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

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

        btnStart.setSafeOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_categoryFragment)
        }

        btnGuide.setSafeOnClickListener {
            openURL(YOUTUBE_GUIDE)
        }

        imgSurvey.setSafeOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_surveyFragment)
        }

        imgSettings.setSafeOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_configurationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}