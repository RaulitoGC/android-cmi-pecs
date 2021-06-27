package com.cmi.presentation.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmi.presentation.R
import com.cmi.presentation.databinding.FragmentConfigurationBinding
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation

class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {

        val context = context
        if (context != null) {
            lyToolbar.txtTitle.text = getString(R.string.text_settings)
            lyToolbar.toolbar.setUpNavigation {
                findNavController().popBackStack()
            }
        }

        cardViewCategory.setSafeOnClickListener {
            navigateToConfigurationOption(flow = FLOW.CATEGORY)
        }

        cardViewPictogram.setSafeOnClickListener {
            navigateToConfigurationOption(flow = FLOW.PICTOGRAM)
        }
    }

    private fun navigateToConfigurationOption(flow: FLOW) {
        val directions =
            ConfigurationFragmentDirections.actionConfigurationFragmentToConfigurationOptionFragment(
                flow
            )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}