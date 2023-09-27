package com.nanda.assig_mandiri.feature.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.databinding.FragmentNewsSourceBinding
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
        val categoryTitle = arguments?.getString("category_name").orEmpty()
        viewModel.fetchNewsSource(category)
        setToolbar(categoryTitle)
        setupAdapter()
        setupObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setToolbar(title: String) = with(binding) {
        tvTitle.text = title
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupAdapter() = with(binding) {
        sourceAdapter = NewsSourceAdapter { id, name ->
            findNavController().navigate(
                R.id.open_news_article,
                bundleOf(
                    "source" to id,
                    "source_name" to name
                )
            )
        }

        rvSource.adapter = sourceAdapter
    }

    private fun setupObserver() {
        viewModel.newsSourceLiveData.observe(viewLifecycleOwner) { sources ->
            sourceAdapter?.submitList(sources)
        }
    }
}