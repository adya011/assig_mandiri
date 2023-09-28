package com.nanda.assig_mandiri.feature.article

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.base.BaseFragment
import com.nanda.assig_mandiri.databinding.FragmentNewsArticleBinding
import com.nanda.assig_mandiri.databinding.LayoutToolbarArticleBinding
import com.nanda.assig_mandiri.util.ARG_SOURCE_NAME
import com.nanda.assig_mandiri.util.ARG_SOURCE_VALUE
import com.nanda.assig_mandiri.util.URL
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsArticleFragment : BaseFragment() {

    private val viewModel by viewModel<NewsArticleViewModel>()

    private var _binding: FragmentNewsArticleBinding? = null
    private val binding get() = _binding!!

    private var articleAdapter: NewsArticleAdapter? = null
    private lateinit var sourceId: String

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
        sourceId = arguments?.getString(ARG_SOURCE_VALUE).orEmpty()
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

    private fun setToolbar(title: String) {
        val toolbarBinding = LayoutToolbarArticleBinding.bind(binding.root)
        var toolbarSearchMode = false

        with(toolbarBinding) {
            tvTitle.text = title
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
            }
            ivLup.setOnClickListener {
                toolbarSearchMode = !toolbarSearchMode
                if (toolbarSearchMode) {
                    tvTitle.visibility = View.GONE
                    tvPageType.visibility = View.GONE
                    etToolbarSearch.visibility = View.VISIBLE
                    tvClear.visibility = View.VISIBLE
                } else {
                    tvTitle.visibility = View.VISIBLE
                    tvPageType.visibility = View.VISIBLE
                    etToolbarSearch.visibility = View.GONE
                    tvClear.visibility = View.GONE
                }
            }

            etToolbarSearch.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    viewModel.setQuery((view as EditText).text.toString())
                    viewModel.fetchNewsArticle(sourceId)
                    etToolbarSearch.setText(viewModel.getQuery())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            tvClear.setOnClickListener {
                if (viewModel.getQuery() != "") {
                    viewModel.clearQuery()
                    viewModel.fetchNewsArticle(sourceId)
                }
                etToolbarSearch.setText(viewModel.getQuery())

            }
        }
    }

    private fun setupAdapter() = with(binding) {
        articleAdapter = NewsArticleAdapter {
            navigateToWebView(it)
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

    private fun navigateToWebView(url: String) {
        findNavController().navigate(
            R.id.open_webview,
            bundleOf(
                URL to url
            )
        )
    }
}