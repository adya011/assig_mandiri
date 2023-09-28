package com.nanda.assig_mandiri.feature.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.base.BaseFragment
import com.nanda.assig_mandiri.databinding.FragmentNewsArticleBinding
import com.nanda.assig_mandiri.util.ARG_SOURCE_NAME
import com.nanda.assig_mandiri.util.ARG_SOURCE_VALUE
import com.nanda.assig_mandiri.util.URL
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsArticleFragment : BaseFragment() {

    private val viewModel by viewModel<NewsArticleViewModel>()

    private var _binding: FragmentNewsArticleBinding? = null
    private val binding get() = _binding!!

    private var articleAdapter: NewsArticleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsArticleBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sourceId = arguments?.getString(ARG_SOURCE_VALUE).orEmpty()
        val sourceName = arguments?.getString(ARG_SOURCE_NAME).orEmpty()
        viewModel.fetchNewsArticle(sourceId)
        setToolbar(sourceName)
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
        articleAdapter = NewsArticleAdapter {
            findNavController().navigate(
                R.id.open_webview,
                bundleOf(
                    URL to it
                )
            )
        }

        rvArticle.adapter = articleAdapter
    }

    private fun setupObserver() {
        viewModel.newsArticleLiveData.observe(viewLifecycleOwner) { articles ->
            articleAdapter?.submitList(articles)
        }
        viewModel.displayChild.observe(viewLifecycleOwner) {
            binding.vfContent.displayedChild = it.first
            binding.tvErrorMessage.text = it.second
        }
    }
}