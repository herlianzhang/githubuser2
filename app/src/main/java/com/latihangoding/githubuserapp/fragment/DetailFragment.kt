package com.latihangoding.githubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.databinding.ActivityListBinding
import com.latihangoding.githubuserapp.adapters.ListViewAdapter
import com.latihangoding.githubuserapp.viewmodels.ListViewModel

class DetailFragment : Fragment(), ListViewAdapter.OnClickListener {

    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val username = arguments?.getString(ARG_USERNAME)
        val isFollowers = arguments?.getBoolean(ARG_FOLLOWERS)
        binding =  DataBindingUtil.inflate(inflater, R.layout.activity_list, container, false)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        if (isFollowers != null && username != null) {
            if (isFollowers) {
                viewModel.getFollowers(username)
            } else {
                viewModel.getFollowing(username)
            }
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter =
            ListViewAdapter(this)
        binding.rvMain.adapter = adapter
        binding.viewModel = viewModel

        viewModel.usersModel.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    override fun onListClick(username: String) {

    }

    companion object {
        const val ARG_USERNAME = "USERNAME"
        const val ARG_FOLLOWERS = "IS_FOLLOWERS"

        fun newInstance(username: String?, isFollowers: Boolean?) : DetailFragment {
            val fragment = DetailFragment()

            val bundle = Bundle().apply {
                putString(ARG_USERNAME, username)
                putBoolean(ARG_FOLLOWERS, isFollowers ?: true)
            }
            fragment.arguments = bundle

            return fragment
        }
    }

}