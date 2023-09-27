package com.nanda.assig_mandiri.feature.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nanda.assig_mandiri.databinding.FragmentNewsCategoryBinding
import com.nanda.assig_mandiri.feature.category.adapter.NewsCategoryAdapter
import com.nanda.assig_mandiri.model.CategoryType
import com.nanda.assig_mandiri.model.NewsCategoryUiState


class NewsCategoryFragment : Fragment() {

    private var _binding: FragmentNewsCategoryBinding? = null
    private val binding get() = _binding!!

    private var categoryAdapter: NewsCategoryAdapter? = null

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
        categoryAdapter = NewsCategoryAdapter().apply {
            submitList(
                listOf(
                    NewsCategoryUiState(name = CategoryType.BUSINESS.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.ENTERTAINMENT.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.GENERAL.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.HEALTH.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.SCIENCE.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.SPORTS.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.HEALTH.value, imgUrl = ""),
                    NewsCategoryUiState(name = CategoryType.TECHNOLOGY.value, imgUrl = "")
                )
            )
        }
        rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}