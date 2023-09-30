package org.d3if3127.submition1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.databinding.FragmentFollowBinding
import org.d3if3127.submition1.viewmodel.FollowersViewModel
import org.d3if3127.submition1.viewmodel.FollowingViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    companion object{
        var ARG_USERNAME = "username"
        var ARG_POSITION = "position"
    }

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val users = activity?.intent?.getStringExtra(ARG_USERNAME)
            if (users != null) {
                ARG_USERNAME = users
            }
            position = it.getInt(ARG_POSITION)
            username = ARG_USERNAME

        }
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        val followersViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[FollowersViewModel::class.java]

            followersViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

        if (position == 1) {
            followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
                setUserData(followers)
            }
        }else{
            val followingViewModel =
                ViewModelProvider(
                    this,
                    ViewModelProvider.NewInstanceFactory()
                )[FollowingViewModel::class.java]
            followingViewModel.following.observe(viewLifecycleOwner) { following ->
                setUserData(following)
            }
        }
    }
    private fun setUserData(itemsItem :List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(itemsItem)
        binding.rvFollow.adapter = adapter

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}