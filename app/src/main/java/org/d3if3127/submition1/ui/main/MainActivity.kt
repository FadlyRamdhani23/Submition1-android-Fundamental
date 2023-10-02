package org.d3if3127.submition1.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import org.d3if3127.submition1.repository.Result
import org.d3if3127.submition1.viewmodel.MainViewModel
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.ui.GithubAdapter
import org.d3if3127.submition1.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         val mainViewModel by viewModels<MainViewModel>(){
            ViewModelFactory.getInstance(application)
        }
        supportActionBar?.hide()

        val githubAdapter = GithubAdapter { github ->
            if (github.isBookmarked){
                mainViewModel.deleteNews(github)
            } else {
                mainViewModel.saveNews(github)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)

        }
        mainViewModel.getHeadlineGithub().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val githubData = result.data
                        githubAdapter.submitList(githubData)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)
        binding?.rvGithub?.apply {
            adapter = githubAdapter
        }


        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { textView, actionId, event ->
            val searchText = binding.searchView.text.toString().trim()
            mainViewModel.setSearchQuery(searchText) // Memasukkan nilai pencarian ke ViewModel
            binding.searchView.hide()
            mainViewModel.getHeadlineGithub().observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val githubData = result.data
                            githubAdapter.submitList(githubData)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            false
        }

    }

//    private fun setGithubData(itemsItem :List<GithubEntity>) {
//        val adapter = GithubAdapter(itemsItem)
//        adapter.submitList(GithubEntity)
//        binding.rvGithub.adapter = adapter
//
//    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}