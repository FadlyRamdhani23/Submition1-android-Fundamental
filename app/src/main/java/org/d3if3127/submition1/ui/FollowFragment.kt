package org.d3if3127.submition1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.d3if3127.submition1.R
import org.d3if3127.submition1.databinding.FragmentFollowBinding
import org.d3if3127.submition1.ui.detail.DetailUserActivity

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: GithubAdapter

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
        if (position == 1) {
           binding.sapi.text = "follower  $username"
        } else {
            binding.sapi.text = "following $username"
        }
    }

}