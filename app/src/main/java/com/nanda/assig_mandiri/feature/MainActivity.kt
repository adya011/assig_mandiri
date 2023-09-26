package com.nanda.assig_mandiri.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var graph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        graph = inflater.inflate(R.navigation.nav_graph_main)
        graph.setStartDestination(R.id.fragment_news_category)
        navHostFragment.navController.graph = graph
    }
}