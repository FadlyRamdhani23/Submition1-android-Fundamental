package org.d3if3127.submition1.ui.detail

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        val avatar = intent.getStringExtra(GITHUB_AVATAR)


        if (user != null) {
            GITHUB_USERNAME = user
        }

        val detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)

        }

        detailViewModel.detailUser.observe(this) { detailUser ->
            setUserData(detailUser)

            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter

            val tabLayoutMediator = TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
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

        var ceked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.cekFav(id)
            withContext(Dispatchers.Main) {
                if (count != null){
                    if (count > 0){
                        binding.toggleButtonFav.isChecked = true
                        ceked = true
                    } else {
                        binding.toggleButtonFav.isChecked = false
                        ceked = false
                    }
                }
            }
        }
        binding.toggleButtonFav.setOnClickListener {
            ceked = !ceked
            binding.toggleButtonFav.isChecked = ceked

            if (ceked) {
                detailViewModel.addFav( id,user.toString(),avatar.toString())
            } else {
                detailViewModel.removeFav(id)
            }
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
        var GITHUB_USERNAME = "username"
        var GITHUB_ID = "id"
        var GITHUB_AVATAR = "avatar"
    }

}