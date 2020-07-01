package com.latihangoding.githubuserapp.list

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.databinding.ActivityListBinding
import com.latihangoding.githubuserapp.detail.DetailActivity
import com.latihangoding.githubuserapp.model.GithubModel
import com.latihangoding.githubuserapp.model.UserModel

class ListActivity : AppCompatActivity(), ListViewAdapter.OnClickListener {



    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        val githubData = intent.getParcelableExtra(USER_DATA) as GithubModel
        val viewModelFactory = ListViewModelFactory(githubData.users)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
        binding.lifecycleOwner = this

        val adapter = ListViewAdapter(this)
        binding.rvMain.adapter = adapter

        viewModel.usersModel.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onListClick(model: UserModel, pair: Pair<View, String>) {
        val optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair.first, pair.second)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(USER_DATA, model as Parcelable)
        startActivity(intent, optionCompat.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // fungsi search
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemLanguage) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val USER_DATA = "USER_DATA"
    }
}