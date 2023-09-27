package com.nanda.assig_mandiri.feature.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.databinding.FragmentNewsSourceBinding


class NewsSourceFragment : Fragment() {

    private var _binding: FragmentNewsSourceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsSourceBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val a = arguments?.getString("category").orEmpty()
        binding.tv.text = a
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}