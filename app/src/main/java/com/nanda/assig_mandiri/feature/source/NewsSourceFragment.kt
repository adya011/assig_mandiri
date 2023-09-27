package com.nanda.assig_mandiri.feature.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.databinding.FragmentNewsSourceBinding
import com.nanda.assig_mandiri.feature.source.adapter.NewsSourceAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsSourceFragment : Fragment() {

    private val viewModel by viewModel<NewsSourceViewModel>()

    private var _binding: FragmentNewsSourceBinding? = null
    private val binding get() = _binding!!

    private var sourceAdapter: NewsSourceAdapter? = null

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
        val category = arguments?.getString("category").orEmpty()
        viewModel.fetchNewsSource(category)
        setToolbar()
        setupAdapter()
        setupObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setToolbar() = with(binding) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupAdapter() = with(binding) {
        sourceAdapter = NewsSourceAdapter {

        }

        rvSource.adapter = sourceAdapter
    }

    private fun setupObserver() {
        viewModel.newsSourceData.observe(viewLifecycleOwner) {
            sourceAdapter?.submitList(it)
        }
    }
}