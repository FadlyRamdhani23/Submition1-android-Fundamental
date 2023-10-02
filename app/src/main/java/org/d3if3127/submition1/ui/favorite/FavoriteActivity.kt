package org.d3if3127.submition1.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.databinding.ActivityFavoriteBinding
import org.d3if3127.submition1.ui.GithubAdapter
import org.d3if3127.submition1.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: GithubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Initialize the adapter
        adapter = GithubAdapter()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = adapter // Set the initialized adapter here
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        viewModel.getFav()?.observe(this) { result ->
            val items = arrayListOf<ItemsItem>()
            result.map {
                val item = ItemsItem(id = it.id, login = it.login, avatarUrl = it.avatar)
                items.add(item)
            }
            adapter.submitList(items)
        }
        setToolbar()
    }
    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}