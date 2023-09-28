package com.nanda.assig_mandiri.feature.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.base.BaseFragment
import com.nanda.assig_mandiri.databinding.FragmentNewsCategoryBinding
import com.nanda.assig_mandiri.model.CategoryType
import com.nanda.assig_mandiri.util.categoryValue
import com.nanda.assig_mandiri.util.categoryName

class NewsCategoryFragment : BaseFragment() {

    private var _binding: FragmentNewsCategoryBinding? = null
    private val binding get() = _binding!!

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
        rvCategory.adapter = NewsCategoryAdapter { navigateToNewsSource(it) }.apply {
            submitList(CategoryType.values().toMutableList())
        }
    }

    private fun navigateToNewsSource(category: CategoryType) {
        findNavController().navigate(
            R.id.open_news_source,
            bundleOf(
                categoryValue to category.value,
                categoryName to category.title
            )
        )
    }
}