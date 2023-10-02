package org.d3if3127.submition1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3127.submition1.R
import org.d3if3127.submition1.databinding.ActivityFavoriteBinding
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.viewmodel.MainViewModel
import org.d3if3127.submition1.viewmodel.ViewModelFactory

class favorite : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainViewModel by viewModels<MainViewModel>(){
            ViewModelFactory.getInstance(application)
        }
        val githubAdapter = GithubAdapter { github ->
            if (github.isBookmarked){
                mainViewModel.deleteNews(github)
            } else {
                mainViewModel.saveNews(github)

            }
        }
        mainViewModel.getBookmarkedGithub().observe(this) { result ->
            if (result != null) {
                githubAdapter.submitList(result)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)
        binding?.rvGithub?.apply {
            adapter = githubAdapter
        }

        }


    }