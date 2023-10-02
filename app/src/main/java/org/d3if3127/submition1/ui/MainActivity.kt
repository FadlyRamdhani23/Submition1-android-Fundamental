package org.d3if3127.submition1.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3127.submition1.R
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.databinding.ActivitySwichBinding
import org.d3if3127.submition1.ui.favorite.FavoriteActivity
import org.d3if3127.submition1.viewmodel.MainViewModel
import org.d3if3127.submition1.viewmodel.ModeViewModel
import org.d3if3127.submition1.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var setBinding: ActivitySwichBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setBinding = ActivitySwichBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
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
        val pref = SettingPreferences.getInstance(application.dataStore)
        val setViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ModeViewModel::class.java
        )
        setViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               setBinding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setBinding.switchTheme.isChecked = false
            }
        }

        setBinding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            setViewModel.saveThemeSetting(isChecked)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menufavorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }R.id.mode -> {
                Intent(this, SwichActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}