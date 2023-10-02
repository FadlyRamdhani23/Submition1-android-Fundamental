package org.d3if3127.submition1.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.tabs.TabLayoutMediator
import org.d3if3127.submition1.R
import org.d3if3127.submition1.data.response.DetailUserResponse
import org.d3if3127.submition1.viewmodel.DetailViewModel
import org.d3if3127.submition1.databinding.ActivityDetailUserBinding
import org.d3if3127.submition1.ui.SectionsPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val user = intent.getStringExtra(GITHUB_USERNAME)
        val id = intent.getIntExtra(GITHUB_ID, 0)
        if (user != null) {
            GITHUB_USERNAME = user
        }
        if (id != 0) {
            GITHUB_ID = id.toString()
        }


        val detailViewModel = ViewModelProvider(this).get(
            DetailViewModel::class.java)
        detailViewModel.isLoading.observe(this) {
            showLoading(it)

        }
        detailViewModel.detailUser.observe(this) { DetailUser ->
                setUserData(DetailUser)
            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter

            val tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.tab_text_1)
                    }
                    1 -> {
                        tab.text = getString(R.string.tab_text_2)
                    }
                }
            }
            tabLayoutMediator.attach()
        }
        binding.toggleButtonFav.setOnClickListener {

        }

    }

    private fun setUserData(DetailUser: DetailUserResponse) {
        binding?.apply {
            akun.text = DetailUser.login
            username.text = DetailUser.name as CharSequence?
            followers.text = "${DetailUser.followers}"
            following.text = "${DetailUser.following}"
            Glide.with(this@DetailUserActivity)
                .load("${DetailUser.avatarUrl}")
                .transform(CircleCrop())
                .into(imageView2)
        }
        binding.followers.text = StringBuilder().append(DetailUser.followers).append(" Followers")
        binding.following.text = StringBuilder().append(DetailUser.following).append(" Following")
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBarr.isVisible = isLoading
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        var GITHUB_USERNAME = " "
        var GITHUB_ID = " "
    }

}