package org.d3if3127.submition1.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.d3if3127.submition1.R
import org.d3if3127.submition1.data.response.DetailUserResponse
import org.d3if3127.submition1.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        var GITHUB_USERNAME = " "
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val user = intent.getStringExtra(DetailUserActivity.GITHUB_USERNAME)
        if (user != null) {
            GITHUB_USERNAME = user
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val DetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        DetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        DetailViewModel.detailUser.observe(this) { DetailUser ->
                setUserData(DetailUser)
        }


    }
    private fun setUserData(DetailUser: DetailUserResponse) {

        binding.akun.text = DetailUser.login
        binding.username.text = DetailUser.name as CharSequence?
        binding.followers.text = DetailUser.followers.toString()+" Followers"
        binding.following.text = DetailUser.following.toString()+" Following"
        Glide.with(this)
            .load("${DetailUser.avatarUrl}")
            .transform(CircleCrop())
            .into(binding.imageView2)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBarr.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}