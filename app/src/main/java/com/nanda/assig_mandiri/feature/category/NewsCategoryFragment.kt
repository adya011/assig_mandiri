package com.nanda.assig_mandiri.feature.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.base.BaseFragment
import com.nanda.assig_mandiri.databinding.FragmentNewsCategoryBinding
import com.nanda.assig_mandiri.model.CategoryType
import com.nanda.assig_mandiri.model.NewsCategoryUiState
import com.nanda.assig_mandiri.util.category
import com.nanda.assig_mandiri.util.categoryName

class NewsCategoryFragment : BaseFragment() {

    private var _binding: FragmentNewsCategoryBinding? = null
    private val binding get() = _binding!!

    private var categoryAdapter: NewsCategoryAdapter? = null

    private val categoryList = listOf(
        NewsCategoryUiState(CategoryType.BUSINESS, image = R.drawable.ic_business),
        NewsCategoryUiState(CategoryType.ENTERTAINMENT, image = R.drawable.ic_entertainment),
        NewsCategoryUiState(CategoryType.GENERAL, image = R.drawable.ic_general),
        NewsCategoryUiState(CategoryType.HEALTH, image = R.drawable.ic_health),
        NewsCategoryUiState(CategoryType.SCIENCE, image = R.drawable.ic_science),
        NewsCategoryUiState(CategoryType.SPORTS, image = R.drawable.ic_sports),
        NewsCategoryUiState(CategoryType.TECHNOLOGY, image = R.drawable.ic_technology)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsCategoryBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupAdapter() = with(binding) {
        categoryAdapter = NewsCategoryAdapter {
            findNavController().navigate(
                R.id.open_news_source,
                bundleOf(
                    category to it.value,
                    categoryName to it.title
                )
            )
        }.apply {
            submitList(categoryList)
        }
        rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}