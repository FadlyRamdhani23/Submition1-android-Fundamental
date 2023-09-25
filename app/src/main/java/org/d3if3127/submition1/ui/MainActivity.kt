package org.d3if3127.submition1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.ui.MainViewModel.Companion.GITHUB_Query


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.githubQuery.observe(this) { githubQuery ->
            setGithubData(githubQuery)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)


        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { textView, actionId, event ->
            val searchText = binding.searchView.text.toString().trim()
            mainViewModel.setSearchQuery(searchText) // Memasukkan nilai pencarian ke ViewModel
            binding.searchView.hide()
            mainViewModel.performSearch()
            false
        }

    }

    private fun setGithubData(itemsItem :List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(itemsItem)
        binding.rvGithub.adapter = adapter

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}