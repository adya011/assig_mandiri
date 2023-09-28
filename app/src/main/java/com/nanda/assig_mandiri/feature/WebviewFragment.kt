package com.nanda.assig_mandiri.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nanda.assig_mandiri.base.BaseFragment
import com.nanda.assig_mandiri.databinding.FragmentWebviewBinding
import com.nanda.assig_mandiri.util.URL


class WebviewFragment : BaseFragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webUrl = arguments?.getString(URL).orEmpty()
        setupWebView(webUrl)
    }

    private fun setupWebView(webUrl: String) = with(binding) {
        webView.loadUrl(webUrl)
    }

}