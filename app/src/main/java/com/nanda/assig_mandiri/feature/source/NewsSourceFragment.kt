package com.nanda.assig_mandiri.feature.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.base.BaseFragment
import com.nanda.assig_mandiri.databinding.FragmentNewsSourceBinding
import com.nanda.assig_mandiri.databinding.LayoutToolbarSourceBinding
import com.nanda.assig_mandiri.util.ARG_CATEGORY_VALUE
import com.nanda.assig_mandiri.util.ARG_CATEGORY_NAME
import com.nanda.assig_mandiri.util.ARG_SOURCE_VALUE
import com.nanda.assig_mandiri.util.ARG_SOURCE_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSourceFragment : BaseFragment() {

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
        val category = arguments?.getString(ARG_CATEGORY_VALUE).orEmpty()
        val categoryTitle = arguments?.getString(ARG_CATEGORY_NAME).orEmpty()
        viewModel.fetchNewsSource(category)
        setToolbar(categoryTitle)
        setupAdapter()
        setupObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setToolbar(title: String) {
        val toolbarBinding = LayoutToolbarSourceBinding.bind(binding.root)
        toolbarBinding.tvTitle.text = title
        toolbarBinding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupAdapter() = with(binding) {
        sourceAdapter = NewsSourceAdapter { id, name ->
            navigateToNewsArticle(id, name)
        }
        rvSource.adapter = sourceAdapter
    }

    private fun setupObserver() {
        viewModel.newsSourceLiveData.observe(viewLifecycleOwner) { sources ->
            sourceAdapter?.submitList(sources)
        }
        viewModel.displayState.observe(viewLifecycleOwner) {
            binding.vfContent.displayedChild = it.displayChild
            binding.tvWarningMessage.text = it.description
        }
    }

    private fun navigateToNewsArticle(id: String, name: String) {
        findNavController().navigate(
            R.id.open_news_article,
            bundleOf(
                ARG_SOURCE_VALUE to id,
                ARG_SOURCE_NAME to name
            )
        )
    }
}